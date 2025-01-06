package mz.mzlib.minecraft.entity.display;

import mz.mzlib.Priority;
import mz.mzlib.event.EventListener;
import mz.mzlib.minecraft.entity.player.EntityPlayer;
import mz.mzlib.minecraft.event.player.EventPlayerJoin;
import mz.mzlib.minecraft.event.player.EventPlayerQuit;
import mz.mzlib.minecraft.event.player.displayentity.EventDisplayEntityDataAsync;
import mz.mzlib.minecraft.event.player.displayentity.EventDisplayEntityDestroyAsync;
import mz.mzlib.minecraft.event.player.displayentity.EventDisplayEntitySpawnAsync;
import mz.mzlib.minecraft.network.packet.PacketListener;
import mz.mzlib.minecraft.network.packet.s2c.play.PacketS2cEntityData;
import mz.mzlib.minecraft.network.packet.s2c.play.PacketS2cEntityDestroy;
import mz.mzlib.minecraft.network.packet.s2c.play.PacketS2cEntitySpawn;
import mz.mzlib.module.MzModule;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DisplayEntityTracker
{
    public static Map<EntityPlayer, DisplayEntityTracker> instances = new ConcurrentHashMap<>();
    
    public static DisplayEntityTracker get(EntityPlayer player)
    {
        // TODO
        return instances.computeIfAbsent(player, k->new DisplayEntityTracker());
    }
    
    public Map<Integer, DisplayEntity> entities = new ConcurrentHashMap<>();
    
    public static class Module extends MzModule
    {
        public static Module instance = new Module();
        
        @Override
        public void onLoad()
        {
            this.register(new EventListener<>(EventPlayerJoin.class, Priority.VERY_VERY_HIGH, event->instances.put(event.getPlayer(), new DisplayEntityTracker())));
            this.register(new EventListener<>(EventPlayerQuit.class, Priority.VERY_VERY_LOW, event->instances.remove(event.getPlayer())));
            
            this.register(new PacketListener<>(PacketS2cEntitySpawn::create, Priority.LOW, (e, p)->
            {
                DisplayEntity displayEntity = new DisplayEntity(e.getPlayer(), p);
                synchronized(displayEntity)
                {
                    get(e.getPlayer()).entities.put(p.getEntityId(), displayEntity);
                    new EventDisplayEntitySpawnAsync(displayEntity, e).call();
                    if(e.isCancelled())
                        get(e.getPlayer()).entities.remove(p.getEntityId(), displayEntity);
                }
            }));
            this.register(new PacketListener<>(PacketS2cEntityDestroy::create, Priority.LOW, (e, p)->
            {
                DisplayEntityTracker tracker = get(e.getPlayer());
                for(int entityId: p.getEntityIds())
                {
                    DisplayEntity displayEntity = tracker.entities.remove(entityId);
                    if(displayEntity==null)
                        continue;
                    new EventDisplayEntityDestroyAsync(displayEntity, e).call();
                }
            }));
            this.register(new PacketListener<>(PacketS2cEntityData::create, Priority.LOW, (e, p)->
            {
                DisplayEntity displayEntity = get(e.getPlayer()).entities.get(p.getEntityId());
                if(displayEntity==null)
                    return;
                synchronized(displayEntity)
                {
                    EventDisplayEntityDataAsync event = new EventDisplayEntityDataAsync(displayEntity, e, p);
                    event.call();
                    if(!e.isCancelled())
                        p.forEachData0(displayEntity::putSyncedData0);
                    event.finish();
                }
            }));
        }
    }
}

package mz.mzlib.minecraft.entity.display;

import mz.mzlib.Priority;
import mz.mzlib.event.EventListener;
import mz.mzlib.minecraft.MinecraftServer;
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
        return instances.get(player);
    }
    
    public Map<Integer, DisplayEntity> entities = new ConcurrentHashMap<>();
    
    public static class Module extends MzModule
    {
        public static Module instance = new Module();
        
        @Override
        public void onLoad()
        {
            this.register(new EventListener<>(EventPlayerJoin.class, Priority.VERY_VERY_HIGH, event->
            {
                instances.put(event.getPlayer(), new DisplayEntityTracker());
                event.runLater(()->
                {
                    if(event.isCancelled())
                        instances.remove(event.getPlayer());
                });
            }));
            for(EntityPlayer player: MinecraftServer.instance.getPlayers())
                instances.put(player, new DisplayEntityTracker());
            this.register(new EventListener<>(EventPlayerQuit.class, Priority.VERY_VERY_LOW, event->instances.remove(event.getPlayer())));
            
            this.register(new PacketListener<>(PacketS2cEntitySpawn::create, Priority.LOW, eventPacket->
            {
                DisplayEntity displayEntity = new DisplayEntity(eventPacket.getPlayer(), eventPacket.getPacket());
                synchronized(displayEntity)
                {
                    get(eventPacket.getPlayer()).entities.put(eventPacket.getPacket().getEntityId(), displayEntity);
                    new EventDisplayEntitySpawnAsync(displayEntity, eventPacket).call();
                    if(eventPacket.isCancelled())
                        get(eventPacket.getPlayer()).entities.remove(eventPacket.getPacket().getEntityId(), displayEntity);
                }
            }));
            this.register(new PacketListener<>(PacketS2cEntityDestroy::create, Priority.LOW, eventPacket->
            {
                DisplayEntityTracker tracker = get(eventPacket.getPlayer());
                if(tracker==null)
                    return;
                for(int entityId: eventPacket.getPacket().getEntityIds())
                {
                    DisplayEntity displayEntity = tracker.entities.remove(entityId);
                    if(displayEntity==null)
                        continue;
                    new EventDisplayEntityDestroyAsync(displayEntity, eventPacket).call();
                }
            }));
            this.register(new PacketListener<>(PacketS2cEntityData::create, Priority.LOW, eventPacket->
            {
                DisplayEntity displayEntity = get(eventPacket.getPlayer()).entities.get(eventPacket.getPacket().getEntityId());
                if(displayEntity==null)
                    return;
                synchronized(displayEntity)
                {
                    EventDisplayEntityDataAsync event = new EventDisplayEntityDataAsync(displayEntity, eventPacket);
                    event.call();
                    if(!eventPacket.isCancelled())
                        eventPacket.getPacket().forEachData0(displayEntity::putData0);
                    event.finish();
                }
            }));
        }
    }
}

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
            
            this.register(new PacketListener<>(PacketS2cEntitySpawn::create, Priority.LOW, packetEvent->
            {
                DisplayEntity displayEntity = new DisplayEntity(packetEvent.getPlayer(), packetEvent.getPacket());
                synchronized(displayEntity)
                {
                    get(packetEvent.getPlayer()).entities.put(packetEvent.getPacket().getEntityId(), displayEntity);
                    new EventDisplayEntitySpawnAsync(displayEntity, packetEvent).call();
                    if(packetEvent.isCancelled())
                        get(packetEvent.getPlayer()).entities.remove(packetEvent.getPacket().getEntityId(), displayEntity);
                }
            }));
            this.register(new PacketListener<>(PacketS2cEntityDestroy::create, Priority.LOW, packetEvent->
            {
                DisplayEntityTracker tracker = get(packetEvent.getPlayer());
                if(tracker==null)
                    return;
                for(int entityId: packetEvent.getPacket().getEntityIds())
                {
                    DisplayEntity displayEntity = tracker.entities.remove(entityId);
                    if(displayEntity==null)
                        continue;
                    new EventDisplayEntityDestroyAsync(displayEntity, packetEvent).call();
                }
            }));
            this.register(new PacketListener<>(PacketS2cEntityData::create, Priority.LOW, packetEvent->
            {
                DisplayEntity displayEntity = get(packetEvent.getPlayer()).entities.get(packetEvent.getPacket().getEntityId());
                if(displayEntity==null)
                    return;
                synchronized(displayEntity)
                {
                    EventDisplayEntityDataAsync event = new EventDisplayEntityDataAsync(displayEntity, packetEvent);
                    event.call();
                    if(!packetEvent.isCancelled())
                        packetEvent.getPacket().forEachData0(displayEntity::putData0);
                    event.finish();
                }
            }));
        }
    }
}

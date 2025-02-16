package mz.mzlib.minecraft.entity.display;

import mz.mzlib.Priority;
import mz.mzlib.event.EventListener;
import mz.mzlib.minecraft.MinecraftServer;
import mz.mzlib.minecraft.entity.player.EntityPlayer;
import mz.mzlib.minecraft.event.player.EventPlayerJoin;
import mz.mzlib.minecraft.event.player.EventPlayerQuit;
import mz.mzlib.minecraft.event.player.async.displayentity.EventAsyncDisplayEntityData;
import mz.mzlib.minecraft.event.player.async.displayentity.EventAsyncDisplayEntityDestroy;
import mz.mzlib.minecraft.event.player.async.displayentity.EventAsyncDisplayEntitySpawn;
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
                DisplayEntityTracker displayEntityTracker = get(packetEvent.getPlayer().unwrap());
                synchronized(displayEntityTracker)
                {
                    DisplayEntity displayEntity = new DisplayEntity(packetEvent.getPlayer().unwrap(), packetEvent.getPacket());
                    EventAsyncDisplayEntitySpawn event = new EventAsyncDisplayEntitySpawn(displayEntity, packetEvent);
                    event.call();
                    if(!event.isCancelled())
                        displayEntityTracker.entities.put(packetEvent.getPacket().getEntityId(), displayEntity);
                    event.finish();
                }
            }));
            this.register(new PacketListener<>(PacketS2cEntityDestroy::create, Priority.LOW, packetEvent->
            {
                DisplayEntityTracker tracker = get(packetEvent.getPlayer().unwrap());
                synchronized(tracker)
                {
                    for(int entityId: packetEvent.getPacket().getEntityIds())
                    {
                        DisplayEntity displayEntity = tracker.entities.get(entityId);
                        if(displayEntity==null)
                            continue;
                        EventAsyncDisplayEntityDestroy event = new EventAsyncDisplayEntityDestroy(displayEntity, packetEvent);
                        event.call();
                        if(!event.isCancelled())
                            tracker.entities.remove(entityId);
                        event.finish();
                    }
                }
            }));
            this.register(new PacketListener<>(PacketS2cEntityData::create, Priority.LOW, packetEvent->
            {
                DisplayEntityTracker displayEntityTracker = get(packetEvent.getPlayer().unwrap());
                synchronized(displayEntityTracker)
                {
                    DisplayEntity displayEntity = displayEntityTracker.entities.get(packetEvent.getPacket().getEntityId());
                    if(displayEntity==null)
                        return;
                    EventAsyncDisplayEntityData event = new EventAsyncDisplayEntityData(displayEntity, packetEvent);
                    event.call();
                    if(!packetEvent.isCancelled())
                        packetEvent.getPacket().forEachData(displayEntity.dataHolder::putData);
                    event.finish();
                }
            }));
        }
    }
}

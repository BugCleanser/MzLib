package mz.mzlib.minecraft.event.player.async.displayentity;

import mz.mzlib.minecraft.entity.display.DisplayEntity;
import mz.mzlib.minecraft.event.player.EventPlayer;
import mz.mzlib.minecraft.event.player.async.EventAsyncByPacket;
import mz.mzlib.minecraft.network.packet.Packet;
import mz.mzlib.minecraft.network.packet.PacketEvent;
import mz.mzlib.module.MzModule;

public class EventAsyncDisplayEntity<P extends Packet> extends EventPlayer implements EventAsyncByPacket<P>
{
    public DisplayEntity displayEntity;
    PacketEvent.Specialized<P> packetEvent;
    public EventAsyncDisplayEntity(DisplayEntity displayEntity, PacketEvent.Specialized<P> packetEvent)
    {
        super(packetEvent.getPlayer().unwrap());
        this.displayEntity = displayEntity;
        this.packetEvent = packetEvent;
    }

    public DisplayEntity getDisplayEntity()
    {
        return this.displayEntity;
    }

    @Override
    public PacketEvent.Specialized<P> getPacketEvent()
    {
        return this.packetEvent;
    }

    @Override
    public void runLater(Runnable runnable) throws UnsupportedOperationException
    {
        if(this.isFinished())
            throw new IllegalStateException("Event finished");
        this.futureTasks.schedule(runnable);
    }

    @Override
    public void call()
    {
    }

    public static class Module extends MzModule
    {
        public static Module instance = new Module();

        @Override
        public void onLoad()
        {
            this.register(EventAsyncDisplayEntity.class);

            this.register(EventAsyncDisplayEntitySpawn.class);
            this.register(EventAsyncDisplayEntityDestroy.class);
            this.register(EventAsyncDisplayEntityData.class);
        }
    }
}

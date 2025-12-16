package mz.mzlib.minecraft.event.window.async;

import mz.mzlib.minecraft.event.player.EventPlayer;
import mz.mzlib.minecraft.event.player.async.EventAsyncByPacket;
import mz.mzlib.minecraft.network.packet.Packet;
import mz.mzlib.minecraft.network.packet.PacketEvent;
import mz.mzlib.module.MzModule;

public abstract class EventAsyncWindow<P extends Packet> extends EventPlayer implements EventAsyncByPacket<P>
{
    PacketEvent.Specialized<P> packetEvent;
    public EventAsyncWindow(PacketEvent.Specialized<P> packetEvent)
    {
        super(packetEvent.getPlayer().unwrap());
        this.packetEvent = packetEvent;
    }

    @Override
    public PacketEvent.Specialized<P> getPacketEvent()
    {
        return this.packetEvent;
    }

    public abstract int getSyncId();

    @Override
    public void call()
    {
        super.call();
    }

    public static class Module extends MzModule
    {
        public static Module instance = new Module();

        @Override
        public void onLoad()
        {
            this.register(EventAsyncWindow.class);

            this.register(EventAsyncWindowAction.Module.instance);
            this.register(EventAsyncWindowClose.Module.instance);
            this.register(EventAsyncWindowAnvilSetName.Module.instance);
        }
    }
}

package mz.mzlib.minecraft.event.window.async;

import mz.mzlib.minecraft.event.player.async.EventAsyncByPacket;
import mz.mzlib.minecraft.network.packet.PacketEvent;
import mz.mzlib.minecraft.network.packet.PacketListener;
import mz.mzlib.minecraft.network.packet.c2s.play.PacketC2sWindowClose;
import mz.mzlib.module.MzModule;

public class EventAsyncWindowClose extends EventAsyncWindow<PacketC2sWindowClose> implements EventAsyncByPacket.Cancellable
{
    public EventAsyncWindowClose(PacketEvent.Specialized<PacketC2sWindowClose> packetEvent)
    {
        super(packetEvent);
    }

    @Override
    public int getSyncId()
    {
        return this.getPacket().getSyncId();
    }

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
            this.register(EventAsyncWindowClose.class);

            this.register(new PacketListener<>(
                PacketC2sWindowClose.FACTORY,
                packetEvent -> new EventAsyncWindowClose(packetEvent).call()
            ));
        }
    }
}

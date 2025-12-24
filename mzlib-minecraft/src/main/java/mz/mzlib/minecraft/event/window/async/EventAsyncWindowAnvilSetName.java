package mz.mzlib.minecraft.event.window.async;

import mz.mzlib.minecraft.MinecraftPlatform;
import mz.mzlib.minecraft.event.player.async.EventAsyncByPacket;
import mz.mzlib.minecraft.network.packet.Packet;
import mz.mzlib.minecraft.network.packet.PacketEvent;
import mz.mzlib.minecraft.network.packet.PacketListener;
import mz.mzlib.minecraft.network.packet.c2s.common.PacketC2sCustom;
import mz.mzlib.minecraft.network.packet.c2s.play.PacketC2sWindowAnvilNameV1300;
import mz.mzlib.module.MzModule;

public abstract class EventAsyncWindowAnvilSetName<P extends Packet> extends EventAsyncWindow<P> implements EventAsyncByPacket.Cancellable
{
    public EventAsyncWindowAnvilSetName(PacketEvent.Specialized<P> packetEvent)
    {
        super(packetEvent);
    }

    @Override
    public int getSyncId()
    {
        throw new UnsupportedOperationException();
    }

    public abstract String getName();

    public abstract void setName(String value);

    @Override
    public void call()
    {
        super.call();
    }

    public static class V_1300 extends EventAsyncWindowAnvilSetName<PacketC2sCustom>
    {
        public V_1300(PacketEvent.Specialized<PacketC2sCustom> packetEvent)
        {
            super(packetEvent);
        }

        @Override
        public String getName()
        {
            this.getPacket().getPayload().getWrapped().markReaderIndex();
            try
            {
                return this.getPacket().getPayload().readString(Short.MAX_VALUE);
            }
            finally
            {
                this.getPacket().getPayload().getWrapped().resetReaderIndex();
            }
        }
        @Override
        public void setName(String value)
        {
            this.getPacket().getPayload().getWrapped().resetWriterIndex();
            this.getPacket().getPayload().writeString(value);
        }
    }

    public static class V1300 extends EventAsyncWindowAnvilSetName<PacketC2sWindowAnvilNameV1300>
    {
        public V1300(PacketEvent.Specialized<PacketC2sWindowAnvilNameV1300> packetEvent)
        {
            super(packetEvent);
        }

        @Override
        public String getName()
        {
            return this.getPacket().getName();
        }
        @Override
        public void setName(String value)
        {
            this.getPacket().setName(value);
        }
    }

    public static class Module extends MzModule
    {
        public static Module instance = new Module();

        @Override
        public void onLoad()
        {
            this.register(EventAsyncWindowAnvilSetName.class);
            if(MinecraftPlatform.instance.getVersion() < 1300)
            {
                this.register(new PacketListener<>(
                    PacketC2sCustom.FACTORY, packetEvent ->
                {
                    if(packetEvent.getPacket().getChannelV_1300().equals("MC|ItemName"))
                        new V_1300(packetEvent).call();
                }
                ));
            }
            else
                this.register(new PacketListener<>(
                    PacketC2sWindowAnvilNameV1300.FACTORY,
                    packetEvent -> new V1300(packetEvent).call()
                ));
        }
    }
}

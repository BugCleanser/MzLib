package mz.mzlib.minecraft.event.player;

import mz.mzlib.minecraft.network.packet.PacketEvent;
import mz.mzlib.minecraft.network.packet.PacketListener;
import mz.mzlib.minecraft.network.packet.c2s.play.PacketC2sChatMessage;
import mz.mzlib.module.MzModule;

public class EventPlayerChatAsync extends EventPlayerByPacket
{
    public PacketC2sChatMessage packet;
    public EventPlayerChatAsync(PacketEvent packetEvent, PacketC2sChatMessage packet)
    {
        super(packetEvent);
        this.packet=packet;
    }

    public String getMessage()
    {
        return this.packet.getMessage();
    }
    public void setMessage(String value)
    {
        this.packet.setMessage(value);
    }

    @Override
    public void call()
    {
    }

    public static class Module extends MzModule
    {
        public static Module instance=new Module();

        @Override
        public void onLoad()
        {
            this.register(EventPlayerChatAsync.class);
            this.register(new PacketListener<>(PacketC2sChatMessage.class, (e, p)->
            {
                EventPlayerChatAsync event=new EventPlayerChatAsync(e, p);
                event.call();
            }));
        }
    }
}

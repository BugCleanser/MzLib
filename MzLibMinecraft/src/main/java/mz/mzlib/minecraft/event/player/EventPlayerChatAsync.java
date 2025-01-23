package mz.mzlib.minecraft.event.player;

import mz.mzlib.minecraft.network.packet.PacketEvent;
import mz.mzlib.minecraft.network.packet.PacketListener;
import mz.mzlib.minecraft.network.packet.c2s.play.PacketC2sChatMessage;
import mz.mzlib.module.MzModule;

public class EventPlayerChatAsync extends EventPlayerByPacket<PacketC2sChatMessage>
{
    public EventPlayerChatAsync(PacketEvent.Specialized<PacketC2sChatMessage> packetEvent)
    {
        super(packetEvent);
    }
    
    public String getMessage()
    {
        return this.getPacket().getMessage();
    }
    public void setMessage(String value)
    {
        this.getPacket().setMessage(value);
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
            this.register(EventPlayerChatAsync.class);
            this.register(new PacketListener<>(PacketC2sChatMessage::create, eventPacket->new EventPlayerChatAsync(eventPacket).call()));
        }
    }
}

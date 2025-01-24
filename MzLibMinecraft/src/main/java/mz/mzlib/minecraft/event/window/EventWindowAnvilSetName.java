package mz.mzlib.minecraft.event.window;

import mz.mzlib.minecraft.MinecraftPlatform;
import mz.mzlib.minecraft.network.packet.Packet;
import mz.mzlib.minecraft.network.packet.PacketEvent;
import mz.mzlib.minecraft.network.packet.PacketListener;
import mz.mzlib.minecraft.network.packet.c2s.play.PacketC2sWindowAnvilNameV1300;
import mz.mzlib.module.MzModule;

public abstract class EventWindowAnvilSetName<P extends Packet> extends EventWindow<P>
{
    public EventWindowAnvilSetName(PacketEvent.Specialized<P> packetEvent)
    {
        super(packetEvent, packetEvent.getPlayer().getCurrentWindow());
    }
    
    public abstract String getName();
    
    public abstract void setName(String value);
    
    @Override
    public void call()
    {
        super.call();
    }
    
    public static class V1300 extends EventWindowAnvilSetName<PacketC2sWindowAnvilNameV1300>
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
            this.register(EventWindowAnvilSetName.class);
            if(MinecraftPlatform.instance.getVersion()<1300)
            {
                // TODO
            }
            else
                this.register(new PacketListener<>(PacketC2sWindowAnvilNameV1300::create, packetEvent->packetEvent.sync(()->new V1300(packetEvent).call())));
        }
    }
}

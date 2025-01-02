package mz.mzlib.minecraft.event.window;

import mz.mzlib.minecraft.MinecraftPlatform;
import mz.mzlib.minecraft.network.packet.PacketEvent;
import mz.mzlib.minecraft.network.packet.PacketListener;
import mz.mzlib.minecraft.network.packet.c2s.play.PacketC2sWindowAnvilNameV1300;
import mz.mzlib.module.MzModule;
import mz.mzlib.util.RuntimeUtil;

public abstract class EventWindowAnvilSetName extends EventWindow
{
    public EventWindowAnvilSetName(PacketEvent packetEvent)
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
    
    public static class V1300 extends EventWindowAnvilSetName
    {
        public PacketC2sWindowAnvilNameV1300 packet;
        public V1300(PacketEvent packetEvent, PacketC2sWindowAnvilNameV1300 packet)
        {
            super(packetEvent);
            this.packet = packet;
        }
        
        @Override
        public String getName()
        {
            return this.packet.getName();
        }
        @Override
        public void setName(String value)
        {
            this.packet.setName(value);
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
            {
                this.register(new PacketListener<>(PacketC2sWindowAnvilNameV1300::create, (event, packet)->event.sync().whenComplete((v, t)->
                {
                    if(t!=null)
                        throw RuntimeUtil.sneakilyThrow(t);
                    EventWindowAnvilSetName e = new V1300(event, packet);
                    e.call();
                })));
            }
        }
    }
}

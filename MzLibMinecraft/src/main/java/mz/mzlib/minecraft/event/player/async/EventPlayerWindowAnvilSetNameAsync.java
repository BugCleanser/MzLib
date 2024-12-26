package mz.mzlib.minecraft.event.player.async;

import mz.mzlib.minecraft.MinecraftPlatform;
import mz.mzlib.minecraft.network.packet.PacketEvent;
import mz.mzlib.minecraft.network.packet.PacketListener;
import mz.mzlib.minecraft.network.packet.c2s.play.PacketC2sWindowAnvilNameV1300;
import mz.mzlib.module.MzModule;

public abstract class EventPlayerWindowAnvilSetNameAsync extends EventPlayerAsync
{
    public EventPlayerWindowAnvilSetNameAsync(PacketEvent packetEvent)
    {
        super(packetEvent);
    }
    
    public abstract String getName();
    public abstract void setName(String value);
    
    @Override
    public void call()
    {
        super.call();
    }
    
    public static class EventPlayerWindowAnvilSetNameAsyncV1300 extends EventPlayerWindowAnvilSetNameAsync
    {
        public PacketC2sWindowAnvilNameV1300 packet;
        public EventPlayerWindowAnvilSetNameAsyncV1300(PacketEvent packetEvent, PacketC2sWindowAnvilNameV1300 packet)
        {
            super(packetEvent);
            this.packet=packet;
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
        public static Module instance=new Module();
        
        @Override
        public void onLoad()
        {
            this.register(EventPlayerWindowAnvilSetNameAsync.class);
            if(MinecraftPlatform.instance.getVersion()<1300)
            {
                // TODO
            }
            else
            {
                this.register(new PacketListener<>(PacketC2sWindowAnvilNameV1300.class, (event, packet) ->
                {
                    EventPlayerWindowAnvilSetNameAsync e=new EventPlayerWindowAnvilSetNameAsyncV1300(event, packet);
                    e.call();
                    event.runLater(e::complete);
                }));
            }
        }
    }
}

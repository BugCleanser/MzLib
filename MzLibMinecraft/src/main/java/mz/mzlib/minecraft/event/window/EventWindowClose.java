package mz.mzlib.minecraft.event.window;

import mz.mzlib.minecraft.network.packet.PacketEvent;
import mz.mzlib.minecraft.network.packet.PacketListener;
import mz.mzlib.minecraft.network.packet.c2s.play.PacketC2sWindowClose;
import mz.mzlib.minecraft.window.Window;
import mz.mzlib.module.MzModule;

public class EventWindowClose extends EventWindow<PacketC2sWindowClose>
{
    public EventWindowClose(PacketEvent.Specialized<PacketC2sWindowClose> packetEvent, Window window)
    {
        super(packetEvent, window);
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
            this.register(EventWindowClose.class);
            
            this.register(new PacketListener<>(PacketC2sWindowClose::create, packetEvent->packetEvent.sync(()->
            {
                Window window = packetEvent.getPlayer().getCurrentWindow();
                if(window.getSyncId()!=packetEvent.getPacket().getSyncId())
                {
                    packetEvent.setCancelled(true);
                    return;
                }
                new EventWindowClose(packetEvent, window).call();
            })));
        }
    }
}

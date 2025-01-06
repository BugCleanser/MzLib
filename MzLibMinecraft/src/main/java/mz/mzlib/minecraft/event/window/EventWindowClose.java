package mz.mzlib.minecraft.event.window;

import mz.mzlib.minecraft.network.packet.PacketEvent;
import mz.mzlib.minecraft.network.packet.PacketListener;
import mz.mzlib.minecraft.network.packet.c2s.play.PacketC2sCloseWindow;
import mz.mzlib.minecraft.window.Window;
import mz.mzlib.module.MzModule;

public class EventWindowClose extends EventWindow
{
    public EventWindowClose(PacketEvent packetEvent, Window window)
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
            
            this.register(new PacketListener<>(PacketC2sCloseWindow::create, (pe, packet)->pe.sync(()->
            {
                Window window = pe.getPlayer().getCurrentWindow();
                if(window.getSyncId()!=packet.getSyncId())
                {
                    pe.setCancelled(true);
                    return;
                }
                EventWindowClose event = new EventWindowClose(pe, window);
                event.call();
            })));
        }
    }
}

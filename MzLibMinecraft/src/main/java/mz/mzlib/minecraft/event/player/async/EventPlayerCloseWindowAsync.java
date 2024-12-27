package mz.mzlib.minecraft.event.player.async;

import mz.mzlib.minecraft.network.packet.PacketEvent;
import mz.mzlib.minecraft.network.packet.PacketListener;
import mz.mzlib.minecraft.network.packet.c2s.play.PacketC2sCloseWindow;
import mz.mzlib.minecraft.window.Window;
import mz.mzlib.module.MzModule;

public class EventPlayerCloseWindowAsync extends EventPlayerAsync
{
    public Window window;
    public EventPlayerCloseWindowAsync(PacketEvent packetEvent, Window window)
    {
        super(packetEvent);
        this.window=window;
    }
    
    public Window getWindow()
    {
        return this.window;
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
            this.register(EventPlayerCloseWindowAsync.class);
            
            this.register(new PacketListener<>(PacketC2sCloseWindow.class, (e, p)->
            {
                Window window=e.getPlayer().getCurrentWindow();
                if(window.getSyncId()!=p.getSyncId())
                    return;
                EventPlayerCloseWindowAsync event=new EventPlayerCloseWindowAsync(e, window);
                event.call();
                e.whenComplete(event::complete);
            }));
        }
    }
}

package mz.mzlib.minecraft.event.window;

import mz.mzlib.minecraft.event.player.EventPlayerByPacket;
import mz.mzlib.minecraft.network.packet.Packet;
import mz.mzlib.minecraft.network.packet.PacketEvent;
import mz.mzlib.minecraft.window.Window;
import mz.mzlib.module.MzModule;

public abstract class EventWindow<P extends Packet> extends EventPlayerByPacket<P>
{
    public Window window;
    public EventWindow(PacketEvent.Specialized<P> packetEvent, Window window)
    {
        super(packetEvent);
        this.window = window;
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
            this.register(EventWindow.class);
            
            this.register(EventWindowAction.Module.instance);
            this.register(EventWindowClose.Module.instance);
            this.register(EventWindowAnvilSetName.Module.instance);
        }
    }
}

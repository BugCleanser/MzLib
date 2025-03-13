package mz.mzlib.minecraft.event.window.async;

import mz.mzlib.minecraft.event.player.async.EventAsyncByPacket;
import mz.mzlib.minecraft.network.packet.Packet;
import mz.mzlib.minecraft.network.packet.PacketEvent;
import mz.mzlib.module.MzModule;

public abstract class EventAsyncWindow<P extends Packet> extends EventAsyncByPacket<P>
{
    protected int syncId;
    public EventAsyncWindow(PacketEvent.Specialized<P> packetEvent, int syncId)
    {
        super(packetEvent);
        this.syncId = syncId;
    }
    
    public int getSyncId()
    {
        return this.syncId;
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
            this.register(EventAsyncWindow.class);
            
            this.register(EventAsyncWindowAction.Module.instance);
            this.register(EventAsyncWindowClose.Module.instance);
            this.register(EventAsyncWindowAnvilSetName.Module.instance);
        }
    }
}

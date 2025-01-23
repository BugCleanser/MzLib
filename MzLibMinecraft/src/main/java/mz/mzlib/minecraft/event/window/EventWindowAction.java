package mz.mzlib.minecraft.event.window;

import mz.mzlib.minecraft.network.packet.PacketEvent;
import mz.mzlib.minecraft.network.packet.PacketListener;
import mz.mzlib.minecraft.network.packet.c2s.play.PacketC2sWindowAction;
import mz.mzlib.minecraft.window.Window;
import mz.mzlib.minecraft.window.WindowActionType;
import mz.mzlib.module.MzModule;

public class EventWindowAction extends EventWindow<PacketC2sWindowAction>
{
    public EventWindowAction(PacketEvent.Specialized<PacketC2sWindowAction> packetEvent, Window window)
    {
        super(packetEvent, window);
    }
    
    public WindowActionType getActionType()
    {
        return this.getPacket().getActionType();
    }
    public void setActionType(WindowActionType value)
    {
        this.getPacket().setActionType(value);
    }
    
    public int getSlotIndex()
    {
        return this.getPacket().getSlotIndex();
    }
    public void setSlotIndex(int value)
    {
        this.getPacket().setSlotIndex(value);
    }
    
    public int getData()
    {
        return this.getPacket().getData();
    }
    public void setData(int value)
    {
        this.getPacket().setData(value);
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
            this.register(EventWindowAction.class);
            
            this.register(new PacketListener<>(PacketC2sWindowAction::create, eventPacket->eventPacket.sync(()->
            {
                Window window = eventPacket.getPlayer().getCurrentWindow();
                if(window.getSyncId()!=eventPacket.getPacket().getSyncId())
                {
                    eventPacket.setCancelled(true);
                    return;
                }
                new EventWindowAction(eventPacket, window).call();
            })));
        }
    }
}

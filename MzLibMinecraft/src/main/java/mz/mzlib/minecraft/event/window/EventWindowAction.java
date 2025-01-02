package mz.mzlib.minecraft.event.window;

import mz.mzlib.minecraft.network.packet.PacketEvent;
import mz.mzlib.minecraft.network.packet.PacketListener;
import mz.mzlib.minecraft.network.packet.c2s.play.PacketC2sWindowAction;
import mz.mzlib.minecraft.window.Window;
import mz.mzlib.minecraft.window.WindowActionType;
import mz.mzlib.module.MzModule;
import mz.mzlib.util.RuntimeUtil;

public class EventWindowAction extends EventWindow
{
    public PacketC2sWindowAction packet;
    public EventWindowAction(PacketEvent packetEvent, Window window, PacketC2sWindowAction packet)
    {
        super(packetEvent, window);
        this.packet = packet;
    }
    
    public WindowActionType getActionType()
    {
        return this.packet.getActionType();
    }
    public void setActionType(WindowActionType value)
    {
        this.packet.setActionType(value);
    }
    
    public int getSlotIndex()
    {
        return this.packet.getSlotIndex();
    }
    public void setSlotIndex(int value)
    {
        this.packet.setSlotIndex(value);
    }
    
    public int getData()
    {
        return this.packet.getData();
    }
    public void setData(int value)
    {
        this.packet.setData(value);
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
            
            this.register(new PacketListener<>(PacketC2sWindowAction.class, (pe, packet) ->pe.sync().whenComplete((v,e)->
            {
                if(e!=null)
                    throw RuntimeUtil.sneakilyThrow(e);
                Window window = pe.getPlayer().getCurrentWindow();
                if(window.getSyncId()!=packet.getSyncId())
                {
                    pe.setCancelled(true);
                    return;
                }
                EventWindowAction event=new EventWindowAction(pe, window, packet);
                event.call();
            })));
        }
    }
}

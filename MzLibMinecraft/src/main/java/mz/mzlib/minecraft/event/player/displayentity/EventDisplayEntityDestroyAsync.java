package mz.mzlib.minecraft.event.player.displayentity;

import mz.mzlib.minecraft.entity.display.DisplayEntity;
import mz.mzlib.minecraft.network.packet.PacketEvent;

public class EventDisplayEntityDestroyAsync extends EventDisplayEntityAsync
{
    public EventDisplayEntityDestroyAsync(DisplayEntity displayEntity, PacketEvent packetEvent)
    {
        super(displayEntity, packetEvent);
    }
    
    @Override
    public void setCancelled(boolean cancelled)
    {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public void call()
    {
        super.call();
    }
}

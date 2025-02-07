package mz.mzlib.minecraft.event.player.async.displayentity;

import mz.mzlib.minecraft.entity.display.DisplayEntity;
import mz.mzlib.minecraft.network.packet.PacketEvent;
import mz.mzlib.minecraft.network.packet.s2c.play.PacketS2cEntityDestroy;

public class EventAsyncDisplayEntityDestroy extends EventAsyncDisplayEntity<PacketS2cEntityDestroy>
{
    public EventAsyncDisplayEntityDestroy(DisplayEntity displayEntity, PacketEvent.Specialized<PacketS2cEntityDestroy> packetEvent)
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

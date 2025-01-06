package mz.mzlib.minecraft.event.player;

import mz.mzlib.minecraft.network.packet.PacketEvent;

public class EventPlayerByPacket extends EventPlayer
{
    PacketEvent packetEvent;
    public EventPlayerByPacket(PacketEvent packetEvent)
    {
        super(packetEvent.getPlayer());
        this.packetEvent = packetEvent;
    }
    
    @Override
    public boolean isCancelled()
    {
        return this.packetEvent.isCancelled();
    }
    @Override
    public void setCancelled(boolean cancelled)
    {
        this.packetEvent.setCancelled(cancelled);
    }
    
    public void sync(Runnable task)
    {
        this.packetEvent.sync(task);
    }
    
    @Override
    public void runLater(Runnable runnable) throws UnsupportedOperationException
    {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public void call()
    {
        super.call();
    }
}

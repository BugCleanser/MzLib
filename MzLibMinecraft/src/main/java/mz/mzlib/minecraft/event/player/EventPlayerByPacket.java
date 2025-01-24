package mz.mzlib.minecraft.event.player;

import mz.mzlib.minecraft.network.packet.Packet;
import mz.mzlib.minecraft.network.packet.PacketEvent;

public class EventPlayerByPacket<P extends Packet> extends EventPlayer
{
    public PacketEvent.Specialized<P> packetEvent;
    public EventPlayerByPacket(PacketEvent.Specialized<P> packetEvent)
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
    
    public PacketEvent.Specialized<P> getPacketEvent()
    {
        return this.packetEvent;
    }
    public P getPacket()
    {
        return this.getPacketEvent().getPacket();
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

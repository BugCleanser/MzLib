package mz.mzlib.minecraft.event.player.async;

import mz.mzlib.minecraft.event.player.EventPlayer;
import mz.mzlib.minecraft.network.packet.Packet;
import mz.mzlib.minecraft.network.packet.PacketEvent;

public class EventAsyncByPacket<P extends Packet> extends EventPlayer
{
    public PacketEvent.Specialized<? extends P> packetEvent;
    public EventAsyncByPacket(PacketEvent.Specialized<? extends P> packetEvent)
    {
        super(packetEvent.getPlayer().unwrap());
        this.packetEvent = packetEvent;
    }
    
    @Override
    public boolean isCancelled()
    {
        return this.packetEvent.isCancelled();
    }
    
    public PacketEvent.Specialized<? extends P> getPacketEvent()
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
    
    public interface Cancellable extends mz.mzlib.event.Cancellable
    {
        @Override
        default void setCancelled(boolean cancelled)
        {
            ((EventAsyncByPacket<?>)this).packetEvent.setCancelled(cancelled);
        }
    }
}

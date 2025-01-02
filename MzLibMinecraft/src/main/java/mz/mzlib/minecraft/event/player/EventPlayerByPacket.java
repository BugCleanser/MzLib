package mz.mzlib.minecraft.event.player;

import mz.mzlib.minecraft.network.packet.PacketEvent;

import java.util.concurrent.CompletableFuture;

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
    
    public CompletableFuture<Void> sync()
    {
        return this.packetEvent.sync();
    }
    
    @Override
    public void whenComplete(Runnable runnable)
    {
        throw new UnsupportedOperationException();
    }
    @Override
    public void call()
    {
        super.call();
    }
}

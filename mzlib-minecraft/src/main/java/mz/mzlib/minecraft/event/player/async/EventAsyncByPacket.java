package mz.mzlib.minecraft.event.player.async;

import mz.mzlib.minecraft.network.packet.Packet;
import mz.mzlib.minecraft.network.packet.PacketEvent;

public interface EventAsyncByPacket<P extends Packet>
{
    PacketEvent.Specialized<? extends P> getPacketEvent();

    default P getPacket()
    {
        return this.getPacketEvent().getPacket();
    }

    default void sync(Runnable task)
    {
        this.getPacketEvent().sync(task);
    }

    interface Cancellable extends mz.mzlib.event.Cancellable
    {
        @Override
        default boolean isCancelled()
        {
            return ((EventAsyncByPacket<?>) this).getPacketEvent().isCancelled();
        }

        @Override
        default void setCancelled(boolean cancelled)
        {
            ((EventAsyncByPacket<?>) this).getPacketEvent().setCancelled(cancelled);
        }
    }
}

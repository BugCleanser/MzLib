package mz.mzlib.event;

public interface Cancellable
{
    default void setCancelled(boolean cancelled)
    {
        ((Event)this).isCancelled = cancelled;
    }
}

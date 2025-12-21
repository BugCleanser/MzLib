package mz.mzlib.event;

public interface Cancellable
{
    default boolean isCancelled()
    {
        return ((Event) this).isCancelled;
    }

    default void setCancelled(boolean cancelled)
    {
        ((Event) this).isCancelled = cancelled;
    }
}

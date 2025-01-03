package mz.mzlib.event;

import java.util.concurrent.CompletableFuture;

/**
 * Every child class must implement {@link #call()} and be registered
 */
public abstract class Event
{
    public CompletableFuture<Void> future = new CompletableFuture<>();
    public boolean isCancelled = false;
    
    /**
     * Execute when the operation corresponding to the event ends or is canceled.
     */
    public void whenComplete(Runnable runnable)
    {
        this.future.thenRun(runnable);
    }
    
    public void setCancelled(boolean cancelled)
    {
        this.isCancelled = cancelled;
    }
    public boolean isCancelled()
    {
        return this.isCancelled;
    }
    
    public void complete()
    {
        this.future.complete(null);
    }
    
    /**
     * Implement this method but do nothing.
     * Invoke to call all the listeners.
     */
    public abstract void call();
}

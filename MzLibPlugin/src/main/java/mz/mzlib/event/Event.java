package mz.mzlib.event;

import java.util.concurrent.CompletableFuture;

public abstract class Event
{
    public CompletableFuture<Void> future=new CompletableFuture<>();
    public boolean isCancelled=false;

    /**
     * Execute when the operation corresponding to the event ends or is canceled.
     */
    public void runLater(Runnable runnable)
    {
        this.future.whenComplete((r,e)->runnable.run());
    }

    public void setCancelled(boolean cancelled)
    {
        this.isCancelled =cancelled;
    }
    public boolean isCancelled()
    {
        return this.isCancelled;
    }

    /**
     * Implement this method but do nothing.
     * Invoke to execute all the listeners.
     */
    public abstract void call();
}

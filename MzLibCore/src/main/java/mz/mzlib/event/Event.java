package mz.mzlib.event;

import mz.mzlib.util.TaskList;

/**
 * Every child class must implement {@link #call()} and be registered
 */
public abstract class Event
{
    public TaskList futureTasks = new TaskList();
    public boolean isCancelled = false;
    
    /**
     * Execute when the operation corresponding to the event ends or is canceled.
     */
    public void runLater(Runnable runnable)
    {
        this.futureTasks.schedule(runnable);
    }
    
    public void setCancelled(boolean cancelled)
    {
        this.isCancelled = cancelled;
    }
    public boolean isCancelled()
    {
        return this.isCancelled;
    }
    
    public void finish()
    {
        this.futureTasks.run();
    }
    
    /**
     * Implement this method but do nothing.
     * Invoke to call all the listeners.
     */
    public abstract void call();
}

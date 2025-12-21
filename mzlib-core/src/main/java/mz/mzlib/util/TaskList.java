package mz.mzlib.util;

import java.util.ArrayList;
import java.util.List;

public class TaskList
{
    public List<Runnable> tasks = new ArrayList<>();

    public void schedule(Runnable task)
    {
        this.tasks.add(task);
    }

    public void run()
    {
        for(Runnable task : this.tasks)
        {
            try
            {
                task.run();
            }
            catch(Throwable e)
            {
                this.onCatch(e);
            }
        }
    }

    public void onCatch(Throwable e)
    {
        e.printStackTrace(System.err);
    }
}

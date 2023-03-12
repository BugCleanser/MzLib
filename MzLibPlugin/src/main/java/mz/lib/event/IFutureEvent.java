package mz.lib.event;

import mz.lib.TypeUtil;
import mz.lib.mzlang.*;

import java.util.*;

public interface IFutureEvent extends Event
{
	@PropAccessor("tasks")
	List<TypeUtil.Runnable> getTasks();
	@PropAccessor("tasks")
	void setTasks(List<TypeUtil.Runnable> tasks);
	
	@Override
	default void init()
	{
		setTasks(new LinkedList<>());
	}
	
	default void doAfter(TypeUtil.Runnable task)
	{
		getTasks().add(task);
	}
	
	/**
	 * Triggered after the event occurs or is cancelled
	 */
	default void done()
	{
		for(TypeUtil.Runnable task:getTasks())
		{
			try
			{
				task.run();
			}
			catch(Throwable e)
			{
				System.out.println("Err on task "+task.getClass().getName());
				e.printStackTrace();
			}
		}
	}
}

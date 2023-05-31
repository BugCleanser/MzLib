package mz.lib.minecraft.bukkit.event;

import mz.lib.TypeUtil;
import org.bukkit.event.Cancellable;

import java.util.List;

public interface IFutureEvent
{
	List<TypeUtil.Runnable> getTasks();
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

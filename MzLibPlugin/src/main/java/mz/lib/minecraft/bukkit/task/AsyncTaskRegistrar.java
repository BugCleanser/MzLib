package mz.lib.minecraft.bukkit.task;

import mz.lib.minecraft.bukkit.*;
import mz.lib.minecraft.task.*;
import mz.lib.module.*;
import mz.lib.module.*;
import mz.lib.minecraft.bukkit.*;
import mz.lib.minecraft.task.*;
import org.bukkit.*;
import org.bukkit.scheduler.*;

import java.util.*;
import java.util.concurrent.*;

public class AsyncTaskRegistrar implements IRegistrar<AsyncTask>
{
	public static AsyncTaskRegistrar instance=new AsyncTaskRegistrar();
	public Map<AsyncTask,BukkitTask> runningTasks=new ConcurrentHashMap<>();
	
	@Override
	public Class<AsyncTask> getType()
	{
		return AsyncTask.class;
	}
	
	@Override
	public boolean register(MzModule module,AsyncTask obj)
	{
		runningTasks.put(obj,Bukkit.getScheduler().runTaskLaterAsynchronously(MzLibBukkitModule.instance.plugin,()->
		{
			try
			{
				obj.runnable.run();
			}
			finally
			{
				module.unreg(obj);
			}
		},obj.delay));
		return true;
	}
	
	@Override
	public void unregister(MzModule module,AsyncTask obj)
	{
		runningTasks.remove(obj).cancel();
	}
}

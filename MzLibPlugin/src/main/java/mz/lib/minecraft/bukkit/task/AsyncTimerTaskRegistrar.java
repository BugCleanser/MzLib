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

public class AsyncTimerTaskRegistrar implements IRegistrar<AsyncTimerTask>
{
	public static AsyncTimerTaskRegistrar instance=new AsyncTimerTaskRegistrar();
	public Map<AsyncTimerTask,BukkitTask> runningTasks=new ConcurrentHashMap<>();
	
	@Override
	public Class<AsyncTimerTask> getType()
	{
		return AsyncTimerTask.class;
	}
	
	@Override
	public boolean register(MzModule module,AsyncTimerTask obj)
	{
		runningTasks.put(obj,Bukkit.getScheduler().runTaskTimerAsynchronously(MzLibBukkitModule.instance.plugin,()->
		{
			try
			{
				obj.runnable.run();
			}
			finally
			{
				module.unreg(obj);
			}
		},obj.delay,obj.period));
		return true;
	}
	
	@Override
	public void unregister(MzModule module,AsyncTimerTask obj)
	{
		runningTasks.remove(obj).cancel();
	}
}

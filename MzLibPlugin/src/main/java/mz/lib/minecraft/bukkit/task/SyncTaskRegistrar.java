package mz.lib.minecraft.bukkit.task;

import mz.lib.minecraft.bukkit.*;
import mz.lib.minecraft.task.*;
import mz.lib.module.*;
import mz.module.*;
import mz.mzlib.bukkit.*;
import mz.mzlib.task.*;
import org.bukkit.*;
import org.bukkit.scheduler.*;

import java.util.*;
import java.util.concurrent.*;

public class SyncTaskRegistrar implements IRegistrar<SyncTask>
{
	public static SyncTaskRegistrar instance=new SyncTaskRegistrar();
	public Map<SyncTask,BukkitTask> runningTasks=new ConcurrentHashMap<>();
	
	@Override
	public Class<SyncTask> getType()
	{
		return SyncTask.class;
	}
	
	@Override
	public boolean register(MzModule module,SyncTask obj)
	{
		runningTasks.put(obj,Bukkit.getScheduler().runTaskLater(MzLibBukkitModule.instance.plugin,()->
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
	public void unregister(MzModule module,SyncTask obj)
	{
		runningTasks.remove(obj).cancel();
	}
}

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

public class SyncTimerTaskRegistrar implements IRegistrar<SyncTimerTask>
{
	public static SyncTimerTaskRegistrar instance=new SyncTimerTaskRegistrar();
	public Map<SyncTimerTask,BukkitTask> runningTasks=new ConcurrentHashMap<>();
	
	@Override
	public Class<SyncTimerTask> getType()
	{
		return SyncTimerTask.class;
	}
	
	@Override
	public boolean register(MzModule module,SyncTimerTask obj)
	{
		runningTasks.put(obj,Bukkit.getScheduler().runTaskTimer(MzLibBukkitModule.instance.plugin,()->
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
	public void unregister(MzModule module,SyncTimerTask obj)
	{
		runningTasks.remove(obj).cancel();
	}
}

package mz.mzlib.mc.bukkit;

import mz.mzlib.mc.MinecraftMainThreadRunner;
import mz.mzlib.mc.SleepTicks;
import mz.mzlib.util.async.AsyncFunction;
import mz.mzlib.util.async.BasicAwait;
import org.bukkit.Bukkit;

public class MinecraftMainThreadRunnerBukkit implements MinecraftMainThreadRunner
{
	public static MinecraftMainThreadRunnerBukkit instance=new MinecraftMainThreadRunnerBukkit();
	
	@Override
	public void schedule(AsyncFunction<?> coroutine)
	{
		Bukkit.getScheduler().runTask(MzLibBukkitPlugin.instance,coroutine::run);
	}
	
	@Override
	public void schedule(AsyncFunction<?> coroutine,BasicAwait await)
	{
		if(await instanceof SleepTicks)
			Bukkit.getScheduler().runTaskLater(MzLibBukkitPlugin.instance,coroutine::run,((SleepTicks)await).ticks);
		else
			throw new UnsupportedOperationException();
	}
}

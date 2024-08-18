package mz.mzlib.mc.bukkit;

import mz.mzlib.mc.MinecraftMainThreadRunner;
import mz.mzlib.mc.SleepTicks;
import mz.mzlib.util.async.AsyncFunction;
import mz.mzlib.util.async.BasicAwait;
import org.bukkit.Bukkit;

public class MinecraftMainThreadRunnerBukkit implements MinecraftMainThreadRunner
{
    public static MinecraftMainThreadRunnerBukkit instance = new MinecraftMainThreadRunnerBukkit();

    @Override
    public void schedule(Runnable function)
    {
        Bukkit.getScheduler().runTask(MzLibBukkitPlugin.instance, function);
    }

    @Override
    public void schedule(Runnable function, BasicAwait await)
    {
        if (await instanceof SleepTicks)
        {
            Bukkit.getScheduler().runTaskLater(MzLibBukkitPlugin.instance, function, ((SleepTicks) await).ticks);
        }
        else
        {
            throw new UnsupportedOperationException();
        }
    }
}

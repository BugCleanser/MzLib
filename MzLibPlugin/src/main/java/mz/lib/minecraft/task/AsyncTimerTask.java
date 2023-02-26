package mz.lib.minecraft.task;

public class AsyncTimerTask
{
	public Runnable runnable;
	public long delay;
	public long period;
	public AsyncTimerTask(Runnable runnable,long period)
	{
		this(runnable,0L,period);
	}
	public AsyncTimerTask(Runnable runnable,long delay,long period)
	{
		this.runnable=runnable;
		this.delay=delay;
		this.period=period;
	}
}

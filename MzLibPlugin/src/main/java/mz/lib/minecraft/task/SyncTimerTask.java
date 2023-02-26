package mz.lib.minecraft.task;

public class SyncTimerTask
{
	public Runnable runnable;
	public long delay;
	public long period;
	public SyncTimerTask(Runnable runnable,long period)
	{
		this(runnable,0L,period);
	}
	public SyncTimerTask(Runnable runnable,long delay,long period)
	{
		this.runnable=runnable;
		this.delay=delay;
		this.period=period;
	}
}

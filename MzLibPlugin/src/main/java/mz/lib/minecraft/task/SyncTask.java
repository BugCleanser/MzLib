package mz.lib.minecraft.task;

public class SyncTask
{
	public Runnable runnable;
	public long delay;
	public SyncTask(Runnable runnable)
	{
		this(runnable,0L);
	}
	public SyncTask(Runnable runnable,long delay)
	{
		this.runnable=runnable;
		this.delay=delay;
	}
}

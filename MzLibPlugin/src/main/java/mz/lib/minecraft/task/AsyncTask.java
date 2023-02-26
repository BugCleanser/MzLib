package mz.lib.minecraft.task;

public class AsyncTask
{
	public Runnable runnable;
	public long delay;
	public AsyncTask(Runnable runnable)
	{
		this(runnable,0L);
	}
	public AsyncTask(Runnable runnable,long delay)
	{
		this.runnable=runnable;
		this.delay=delay;
	}
}

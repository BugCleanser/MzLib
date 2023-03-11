package mz.lib;

import mz.lib.minecraft.task.*;
import mz.lib.module.*;

public class Saver implements Registrable
{
	public long delay=5000;
	public long lastSaved;
	public MzModule module;
	public SyncTask task;
	public Saver(Runnable runnable)
	{
		this.lastSaved=0;
		this.task=new SyncTask(runnable,delay);
	}
	public void save()
	{
		if(System.currentTimeMillis()-lastSaved<delay)
		{
			if(!module.hasReged(task))
				module.reg(task);
		}
		else
			task.runnable.run();
		lastSaved=System.currentTimeMillis();
	}
	
	@Override
	public void onReg(MzModule module)
	{
		this.module=module;
	}
	
	@Override
	public void onUnreg(MzModule module)
	{
		if(module.unreg(task))
			task.runnable.run();
	}
}

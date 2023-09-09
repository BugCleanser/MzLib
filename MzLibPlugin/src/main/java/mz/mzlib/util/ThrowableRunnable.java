package mz.mzlib.util;

public interface ThrowableRunnable extends Runnable
{
	void runWithThrowable() throws Throwable;
	
	@Override
	default void run()
	{
		try
		{
			runWithThrowable();
		}
		catch(Throwable e)
		{
			throw RuntimeUtil.sneakilyThrow(e);
		}
	}
}

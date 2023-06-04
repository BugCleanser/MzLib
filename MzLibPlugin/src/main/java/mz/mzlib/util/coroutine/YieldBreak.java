package mz.mzlib.util.coroutine;

public class YieldBreak implements Yield
{
	@Override
	public void run(Coroutine coroutine)
	{
		if(coroutine.nextCoroutine!=null)
			coroutine.nextCoroutine.run();
	}
}

package mz.mzlib.util.coroutine;

public abstract class Coroutine
{
	protected Object data;
	public Coroutine nextCoroutine;
	
	public abstract Yield template();
	
	public boolean isRunning()
	{
		return data!=null;
	}
	
	public Yield run()
	{
		if(isRunning()) //start
		{
			((Coroutine)data).template().run((Coroutine)data);
			return null;
		}
		else
		{
			//TODO: gen a class by template, allocate a instance to data
			((Coroutine)data).template().run((Coroutine)data);
			if(isRunning())
				return c->nextCoroutine=c;
			else
				return Coroutine::run;
		}
	}
}

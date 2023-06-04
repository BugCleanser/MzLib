package mz.mzlib.util.coroutine;

public abstract class Coroutine
{
	protected Object data;
	public Coroutine nextCoroutine;
	
	public abstract Yield template();
	
	public Yield run()
	{
		//TODO
		return c->nextCoroutine=c;
	}
}

package mz.mzlib.javautil.delegator;

public abstract class AbsDelegator implements Delegator
{
	public Object delegate;
	
	public AbsDelegator(Object delegate)
	{
		this.delegate=delegate;
	}
	
	public Object getDelegate()
	{
		return delegate;
	}
	public void setDelegate(Object delegate)
	{
		this.delegate=delegate;
	}
	
	@Override
	@SuppressWarnings("all")
	public Delegator clone()
	{
		//TODO: NPE
		return clone0();
	}
}

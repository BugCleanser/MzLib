package mz.mzlib.javautil.delegator;

import java.util.Objects;

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
	public String toString()
	{
		return Objects.toString(delegate);
	}
	
	@Override
	public int hashCode()
	{
		return Objects.hashCode(delegate);
	}
	
	@Override
	public boolean equals(Object obj)
	{
		return obj instanceof Delegator&&Objects.equals(delegate,((Delegator)obj).getDelegate()) || Objects.equals(delegate,obj);
	}
	
	@Override
	@SuppressWarnings("all")
	public Delegator clone()
	{
		//TODO: NPE
		return clone0();
	}
}

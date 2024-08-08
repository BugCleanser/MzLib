package mz.mzlib.util.delegator;

import java.util.Objects;

public abstract class AbsDelegator implements Delegator
{
	public Object delegate;
	
	public AbsDelegator(Object delegate)
	{
		this.delegate=delegate;
	}
	
	@Override
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
		return Objects.toString(this.getDelegate());
	}
	
	@Override
	public int hashCode()
	{
		return Objects.hashCode(this.getDelegate());
	}
	
	@Override
	public boolean equals(Object obj)
	{
		return obj instanceof Delegator&&Objects.equals(this.getDelegate(),((Delegator)obj).getDelegate());
	}
	
	@Override
	@SuppressWarnings("all")
	public Delegator clone()
	{
		return clone0();
	}
}

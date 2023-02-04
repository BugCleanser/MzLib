package mz.lib.wrapper;

import java.util.*;

public abstract class AbsWrappedObject implements WrappedObject
{
	public Object raw;
	public List<WrappedObject> superWrappers;
	
	@Deprecated
	public AbsWrappedObject(Object raw)
	{
		superWrappers=new ArrayList<>();
		this.raw=raw;
	}
	
	@Override
	public void setRaw(Object raw)
	{
		this.raw=raw;
	}
	@Override
	public Object getRaw()
	{
		return raw;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		return equals0(obj);
	}
	@Override
	public AbsWrappedObject clone()
	{
		return (AbsWrappedObject) clone0().cast(this.getWrapper());
	}
	@Override
	public int hashCode()
	{
		return hashCode0();
	}
	@Override
	public String toString()
	{
		return toString0();
	}
}

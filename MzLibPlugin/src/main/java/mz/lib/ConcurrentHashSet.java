package mz.lib;

import java.util.*;
import java.util.concurrent.*;

public class ConcurrentHashSet<T> extends AbstractSet<T>
{
	public ConcurrentHashMap<T,T> map=new ConcurrentHashMap<>();
	
	@Override
	public Iterator<T> iterator()
	{
		return map.keySet().iterator();
	}
	
	@Override
	public boolean contains(Object o)
	{
		return map.containsKey(o);
	}
	
	@Override
	public boolean add(T t)
	{
		return map.put(t,t)==null;
	}
	
	@Override
	public boolean remove(Object o)
	{
		return map.remove(o)!=null;
	}
	
	@Override
	public void clear()
	{
		map.clear();
	}
	
	@Override
	public boolean equals(Object o)
	{
		if(o instanceof ConcurrentHashSet)
			return map.equals(((ConcurrentHashSet<?>)o).map);
		return super.equals(o);
	}
	
	@Override
	public int hashCode()
	{
		return map.hashCode();
	}
	
	@Override
	public int size()
	{
		return map.size();
	}
}

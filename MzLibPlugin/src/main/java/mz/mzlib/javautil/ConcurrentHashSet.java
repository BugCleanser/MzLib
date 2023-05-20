package mz.mzlib.javautil;

import java.util.AbstractSet;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

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
		return map.containsKey(RuntimeUtil.<T>forceCast(o));
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
package mz.lib;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class DoubleKeyList<A,B,V>
{
	public@Deprecated Map<A,List<V>> a;
	public@Deprecated Map<B,List<V>> b;
	
	public DoubleKeyList(Map<A,List<V>> a,Map<B,List<V>> b)
	{
		this.a=a;
		this.b=b;
	}
	
	public void add(A k1,B k2,V value)
	{
		if(!a.containsKey(k1))
			a.put(k1,new LinkedList<>());
		a.get(k1).add(value);
		if(!b.containsKey(k1))
			b.put(k2,new LinkedList<>());
		b.get(k1).add(value);
	}
	
	@Deprecated
	public List<V> get1(A key)
	{
		return a.get(key);
	}
	@Deprecated
	public List<V> get2(B key)
	{
		return b.get(key);
	}
	
	public void forEach1(A key,Consumer<V> i)
	{
		List<V> vs=get1(key);
		if(vs!=null)
			vs.forEach(i);
	}
	public void forEach2(B key,Consumer<V> i)
	{
		List<V> vs=get2(key);
		if(vs!=null)
			vs.forEach(i);
	}
	
	public List<V> remove1(A key)
	{
		List<V> vs=a.get(key);
		if(a!=null)
		{
			b.forEach((k,v)->
			{
				v.removeAll(vs);
			});
			a.remove(key);
			return vs;
		}
		return new ArrayList<>();
	}
	public List<V> remove2(B key)
	{
		List<V> vs=b.get(key);
		if(b!=null)
		{
			a.forEach((k,v)->
			{
				v.removeAll(vs);
			});
			b.remove(key);
			return vs;
		}
		return new ArrayList<>();
	}
}

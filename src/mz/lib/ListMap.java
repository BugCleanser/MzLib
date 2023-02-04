package mz.lib;

import java.util.*;
import java.util.function.Supplier;

public class ListMap<K,V> implements Map<K,List<V>>
{
	public Map<K,List<V>> map;
	public Supplier<List> listGenerator=LinkedList::new;
	
	public ListMap(Map<K,List<V>> map)
	{
		this.map=map;
	}
	
	public void add(K key,V value)
	{
		List<V> list=map.get(key);
		if(list==null)
		{
			list=listGenerator.get();
			map.put(key,list);
		}
		list.add(value);
	}
	
	public void removeValue(V value)
	{
		Iterator<Entry<K,List<V>>> i=map.entrySet().iterator();
		while(i.hasNext())
		{
			Entry<K,List<V>> e=i.next();
			if(e.getValue().remove(value))
			{
				if(e.getValue().isEmpty())
					i.remove();
			}
		}
	}
	
	
	@Override
	public int size()
	{
		return map.size();
	}
	@Override
	public boolean isEmpty()
	{
		return map.isEmpty();
	}
	@Override
	public boolean containsKey(Object key)
	{
		return map.containsKey(key);
	}
	@Override
	public boolean containsValue(Object value)
	{
		return map.containsValue(value);
	}
	@Override
	public List<V> get(Object key)
	{
		return map.get(key);
	}
	@Nullable
	@Override
	public List<V> put(K key,List<V> value)
	{
		return map.put(key,value);
	}
	@Override
	public List<V> remove(Object key)
	{
		return map.remove(key);
	}
	@Override
	public void putAll(@NotNull Map<? extends K,? extends List<V>> m)
	{
		map.putAll(m);
	}
	@Override
	public void clear()
	{
		map.clear();
	}
	@NotNull
	@Override
	public Set<K> keySet()
	{
		return map.keySet();
	}
	@NotNull
	@Override
	public Collection<List<V>> values()
	{
		return map.values();
	}
	@NotNull
	@Override
	public Set<Entry<K,List<V>>> entrySet()
	{
		return map.entrySet();
	}
}

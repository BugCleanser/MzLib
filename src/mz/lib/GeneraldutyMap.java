package mz.lib;

import java.util.AbstractMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 我甚至闲着没事干写了个这个
 * 《通用Map》
 */
public class GeneraldutyMap<K,V> extends AbstractMap<K,V>
{
	public @Deprecated
	Set<Entry<K,V>> entrySet=new HashSet<Map.Entry<K,V>>();
	
	@Override
	public Set<Entry<K,V>> entrySet()
	{
		return entrySet;
	}
	@Override
	public V put(K key,V value)
	{
		for(Entry<K,V> e: entrySet)
		{
			if(e.getKey().equals(key))
				return e.setValue(value);
		}
		entrySet.add(new MapEntry<>(key,value));
		return null;
	}
}

package mz.mzlib.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MapUtil
{
	private MapUtil() {}
	
	public static <K,V> HashMap<K,V> newHashMap(Set<? extends Map.Entry<K,V>> entries)
	{
		HashMap<K,V> result=new HashMap<>();
		for(Map.Entry<K,V> i:entries)
			result.put(i.getKey(),i.getValue());
		return result;
	}
}

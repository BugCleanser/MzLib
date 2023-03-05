package mz.lib;

import java.util.*;

public class MapUtil
{
	public static <K,V> int putAllAbsent(Map<K,V> map,Map<K,V> all)
	{
		int num=0;
		for(Map.Entry<K,V> e:all.entrySet())
			if(map.putIfAbsent(e.getKey(),e.getValue())==null)
				num++;
		return num;
	}
}

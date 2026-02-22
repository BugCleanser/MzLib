package mz.mzlib.util;

import org.jetbrains.annotations.ApiStatus;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@ApiStatus.Experimental
public class MapUtil
{
    private MapUtil()
    {
    }

    public static <K, V> HashMap<K, V> newHashMap(Set<? extends Map.Entry<K, V>> entries)
    {
        HashMap<K, V> result = new HashMap<>();
        for(Map.Entry<K, V> i : entries)
        {
            result.put(i.getKey(), i.getValue());
        }
        return result;
    }
}

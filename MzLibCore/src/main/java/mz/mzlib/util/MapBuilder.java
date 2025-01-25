package mz.mzlib.util;

import java.util.HashMap;
import java.util.Map;

public class MapBuilder
{
    Map<Object, Object> map;
    
    public MapBuilder(Map<?, ?> map)
    {
        this.map = RuntimeUtil.cast(map);
    }
    
    public MapBuilder put(Object key, Object value)
    {
        this.map.put(key, value);
        return this;
    }
    
    public <T extends Map<?, ?>> T get()
    {
        return RuntimeUtil.cast(this.map);
    }
    
    public static MapBuilder hashMap()
    {
        return new MapBuilder(new HashMap<>());
    }
}

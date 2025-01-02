package mz.mzlib.util;

import java.util.Map;
import java.util.Optional;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;

public class ThreadLocalGrowingHashMap<K, V> extends ConcurrentHashMap<K, V>
{
    public ThreadLocal<Map<K, Optional<V>>> threadLocal = ThreadLocal.withInitial(WeakHashMap::new);
    
    @Override
    public V get(Object key)
    {
        if(!this.threadLocal.get().containsKey(RuntimeUtil.<K>cast(key)))
            this.threadLocal.get().put(RuntimeUtil.cast(key), Optional.ofNullable(super.get(key)));
        return this.threadLocal.get().get(key).orElse(null);
    }
    
    @Override
    public boolean containsKey(Object key)
    {
        this.get(key);
        return this.threadLocal.get().get(RuntimeUtil.<K>cast(key))!=null;
    }
}

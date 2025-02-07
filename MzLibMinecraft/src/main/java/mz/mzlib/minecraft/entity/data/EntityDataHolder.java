package mz.mzlib.minecraft.entity.data;

import java.util.Map;
import java.util.function.BiConsumer;

public interface EntityDataHolder
{
    Object getData(EntityDataKey key);
    
    Object putData(EntityDataKey key, Object value);
    
    Object removeData(EntityDataKey key);
    
    void forEachData(BiConsumer<EntityDataKey, Object> action);
    
    default boolean hasData(EntityDataKey key)
    {
        return getData(key)!=null;
    }
    
    default <T> T getData(EntityDataAdapter<T> adapter)
    {
        return adapter.get(this);
    }
    default <T> T putData(EntityDataAdapter<T> adapter, T value)
    {
        return adapter.put(this, value);
    }
    default <T> T removeData(EntityDataAdapter<T> adapter)
    {
        return adapter.remove(this);
    }
    default boolean hasData(EntityDataAdapter<?> adapter)
    {
        return this.hasData(adapter.getKey());
    }
    
    static EntityDataHolder of(Map<EntityDataKey, Object> map)
    {
        return new ByMap(map);
    }
    
    class ByMap implements EntityDataHolder
    {
        Map<EntityDataKey, Object> map;
        public ByMap(Map<EntityDataKey, Object> map)
        {
            this.map = map;
        }
        @Override
        public Object getData(EntityDataKey key)
        {
            return this.map.get(key);
        }
        @Override
        public Object putData(EntityDataKey key, Object value)
        {
            return this.map.put(key, value);
        }
        @Override
        public Object removeData(EntityDataKey key)
        {
            return this.map.remove(key);
        }
        @Override
        public boolean hasData(EntityDataKey key)
        {
            return this.map.containsKey(key);
        }
        @Override
        public void forEachData(BiConsumer<EntityDataKey, Object> action)
        {
            this.map.forEach(action);
        }
    }
}

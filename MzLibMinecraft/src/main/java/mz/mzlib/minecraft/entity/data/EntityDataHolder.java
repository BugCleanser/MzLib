package mz.mzlib.minecraft.entity.data;

import mz.mzlib.util.Option;

import java.util.Map;
import java.util.function.BiConsumer;

public interface EntityDataHolder
{
    Option<Object> getData(EntityDataKey key);
    
    Option<Object> putData(EntityDataKey key, Object value);
    
    Option<Object> removeData(EntityDataKey key);
    
    void forEachData(BiConsumer<EntityDataKey, Object> action);
    
    default boolean hasData(EntityDataKey key)
    {
        return getData(key)!=null;
    }
    
    default <T> Option<T> getData(EntityDataAdapter<T> adapter)
    {
        return adapter.get(this);
    }
    @SuppressWarnings("UnusedReturnValue")
    default <T> Option<T> putData(EntityDataAdapter<T> adapter, T value)
    {
        return adapter.put(this, value);
    }
    default <T> Option<T> removeData(EntityDataAdapter<T> adapter)
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
        public Option<Object> getData(EntityDataKey key)
        {
            return Option.fromNullable(this.map.get(key));
        }
        @Override
        public Option<Object> putData(EntityDataKey key, Object value)
        {
            return Option.fromNullable(this.map.put(key, value));
        }
        @Override
        public Option<Object> removeData(EntityDataKey key)
        {
            return Option.fromNullable(this.map.remove(key));
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

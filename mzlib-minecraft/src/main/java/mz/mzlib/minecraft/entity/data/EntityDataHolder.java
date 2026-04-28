package mz.mzlib.minecraft.entity.data;

import mz.mzlib.util.RuntimeUtil;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.function.BiConsumer;

// TODO
@ApiStatus.Experimental
public interface EntityDataHolder
{
    <T> @Nullable T getData(EntityDataKey<T> key);
    
    <T> @Nullable T putData(EntityDataKey<T> key, T value);
    
    <T> @Nullable T removeData(EntityDataKey<T> key);
    
    @ApiStatus.Experimental
    void forEachData(BiConsumer<EntityDataKey<?>, Object> action);
    
    default boolean hasData(EntityDataKey<?> key)
    {
        return this.getData(key)!=null;
    }
    
    default <T> @Nullable T getData(EntityDataAdapter<T> adapter)
    {
        return adapter.get(this);
    }
    
    @SuppressWarnings("UnusedReturnValue")
    default <T> @Nullable T putData(EntityDataAdapter<T> adapter, T value)
    {
        return adapter.put(this, value);
    }
    
    default <T> @Nullable T removeData(EntityDataAdapter<T> adapter)
    {
        return adapter.remove(this);
    }
    
    default boolean hasData(EntityDataAdapter<?> adapter)
    {
        return this.hasData(adapter.getKey());
    }
    
    @ApiStatus.Experimental
    static EntityDataHolder of(Map<EntityDataKey<?>, Object> map)
    {
        return new ByMap(map);
    }
    
    class ByMap implements EntityDataHolder
    {
        private final Map<EntityDataKey<?>, Object> map;
        public ByMap(Map<EntityDataKey<?>, Object> map)
        {
            this.map = map;
        }
        @Override
        public <T> @Nullable T getData(EntityDataKey<T> key)
        {
            return RuntimeUtil.<@Nullable T>cast(this.map.get(key));
        }
        @Override
        public <T> @Nullable T putData(EntityDataKey<T> key, T value)
        {
            return RuntimeUtil.<@Nullable T>cast(this.map.put(key, value));
        }
        @Override
        public <T> @Nullable T removeData(EntityDataKey<T> key)
        {
            return RuntimeUtil.<@Nullable T>cast(this.map.remove(key));
        }
        @Override
        public boolean hasData(EntityDataKey<?> key)
        {
            return this.map.containsKey(key);
        }
        @Override
        public void forEachData(BiConsumer<EntityDataKey<?>, Object> action)
        {
            this.map.forEach(action);
        }
    }
}

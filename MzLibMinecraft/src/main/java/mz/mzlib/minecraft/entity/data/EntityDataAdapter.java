package mz.mzlib.minecraft.entity.data;

import mz.mzlib.util.InvertibleFunction;

public class EntityDataAdapter<T>
{
    public EntityDataKey key;
    public InvertibleFunction<T, Object> function;
    
    public EntityDataAdapter(EntityDataKey key, InvertibleFunction<T, Object> function)
    {
        this.key = key;
        this.function = function;
    }
    
    public EntityDataKey getKey()
    {
        return this.key;
    }
    
    public T get(EntityDataHolder holder)
    {
        return this.function.inverse().apply(holder.getData(this.key));
    }
    public T put(EntityDataHolder holder, T value)
    {
        Object result = holder.putData(this.key, this.function.apply(value));
        if(result == null)
            return null;
        return this.function.inverse().apply(result);
    }
    public T remove(EntityDataHolder holder)
    {
        Object result = holder.removeData(this.key);
        if(result == null)
            return null;
        return this.function.inverse().apply(result);
    }
}

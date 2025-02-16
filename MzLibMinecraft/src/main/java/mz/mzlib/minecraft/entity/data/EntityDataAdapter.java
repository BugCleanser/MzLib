package mz.mzlib.minecraft.entity.data;

import mz.mzlib.util.InvertibleFunction;
import mz.mzlib.util.Option;

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
    
    public Option<T> get(EntityDataHolder holder)
    {
        return holder.getData(this.key).map(this.function.inverse());
    }
    public Option<T> put(EntityDataHolder holder, T value)
    {
        return holder.putData(this.key, this.function.apply(value)).map(this.function.inverse());
    }
    public Option<T> remove(EntityDataHolder holder)
    {
        return holder.removeData(this.key).map(this.function.inverse());
    }
}

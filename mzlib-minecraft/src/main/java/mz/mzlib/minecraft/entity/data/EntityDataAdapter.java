package mz.mzlib.minecraft.entity.data;

import mz.mzlib.util.FunctionInvertible;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

public class EntityDataAdapter<T>
{
    private final Impl<T, ?> impl;
    public <U> EntityDataAdapter(EntityDataKey<U> key, FunctionInvertible<T, U> function)
    {
        this.impl = new Impl<>(key, function);
    }

    public EntityDataKey<?> getKey()
    {
        return this.impl.getKey();
    }
    @ApiStatus.Internal
    public FunctionInvertible<T, ?> getFunction()
    {
        return this.impl.getFunction();
    }

    public @Nullable T get(EntityDataHolder holder)
    {
        return this.impl.get(holder);
    }
    public @Nullable T put(EntityDataHolder holder, T value)
    {
        return this.impl.put(holder, value);
    }
    public @Nullable T remove(EntityDataHolder holder)
    {
        return this.impl.remove(holder);
    }

    private static class Impl<T, U>
    {
        private final EntityDataKey<U> key;
        private final FunctionInvertible<T, U> function;

        public Impl(EntityDataKey<U> key, FunctionInvertible<T, U> function)
        {
            this.key = key;
            this.function = function;
        }

        public EntityDataKey<U> getKey()
        {
            return this.key;
        }
        public FunctionInvertible<T, U> getFunction()
        {
            return this.function;
        }

        public @Nullable T get(EntityDataHolder holder)
        {
            @Nullable U result = holder.getData(this.key);
            if(result == null)
                return null;
            return this.function.inverse().apply(result);
        }
        public @Nullable T put(EntityDataHolder holder, T value)
        {
            @Nullable U result = holder.putData(this.key, this.function.apply(value));
            if(result == null)
                return null;
            return this.function.inverse().apply(result);
        }
        public @Nullable T remove(EntityDataHolder holder)
        {
            @Nullable U result = holder.removeData(this.key);
            if(result == null)
                return null;
            return this.function.inverse().apply(result);
        }
    }
}

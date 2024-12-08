package mz.mzlib.util;

import java.util.Objects;

public class StrongRef<T> implements Ref<T>
{
    public T target;

    public StrongRef(T value)
    {
        set(value);
    }

    @Override
    public T get()
    {
        return target;
    }

    @Override
    public void set(T value)
    {
        target = value;
    }

    @Override
    public boolean equals(Object obj)
    {
        return obj instanceof Ref && get() == ((Ref<?>) obj).get();
    }

    @Override
    public int hashCode()
    {
        return Objects.hashCode(get());
    }

    @Override
    public String toString()
    {
        return Objects.toString(get());
    }
}

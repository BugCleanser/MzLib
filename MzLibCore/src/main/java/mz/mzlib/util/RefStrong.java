package mz.mzlib.util;

import java.util.Objects;

public class RefStrong<T> implements Ref<T>
{
    public T target;
    
    public RefStrong(T value)
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
        return obj instanceof Ref && get()==((Ref<?>)obj).get();
    }
    
    @Override
    public int hashCode()
    {
        return System.identityHashCode(this.get());
    }
    
    @Override
    public String toString()
    {
        return Objects.toString(get());
    }
}

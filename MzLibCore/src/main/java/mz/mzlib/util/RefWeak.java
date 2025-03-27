package mz.mzlib.util;

import java.lang.ref.WeakReference;
import java.util.Objects;

public class RefWeak<T> implements Ref<T>
{
    public WeakReference<T> ref;
    public int hashCode;

    public RefWeak(T value)
    {
        this.set(value);
    }

    @Override
    public T get()
    {
        return this.ref.get();
    }

    @Override
    public void set(T value)
    {
        this.ref = new WeakReference<>(value);
        this.hashCode = System.identityHashCode(value);
    }

    @Override
    public boolean equals(Object obj)
    {
        return obj instanceof Ref && get() == ((Ref<?>) obj).get();
    }

    @Override
    public int hashCode()
    {
        return this.hashCode;
    }

    @Override
    public String toString()
    {
        return Objects.toString(get());
    }
}

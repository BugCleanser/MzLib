package mz.mzlib.util;

import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class RefStrong<T extends @Nullable Object> implements Ref<T>
{
    public @Nullable T target;

    public RefStrong(@Nullable T value)
    {
        this.set(value);
    }

    public static <T> RefStrong<T> of(@Nullable T value)
    {
        return new RefStrong<>(value);
    }
    public static <T> RefStrong<T> ofNull()
    {
        return of(null);
    }

    @Override
    public @Nullable T get()
    {
        return target;
    }

    @Override
    public void set(@Nullable T value)
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
        return System.identityHashCode(this.get());
    }

    @Override
    public String toString()
    {
        return Objects.toString(get());
    }
}

package mz.mzlib.util;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.function.Function;

@ApiStatus.NonExtendable
public class Box<T extends @Nullable Object>
{
    protected T value;
    public Box(T value)
    {
        this.value = value;
    }

    public static <T extends @Nullable Object> Mut<T> of(T value)
    {
        return new Mut<>(value);
    }

    public T get()
    {
        return this.value;
    }

    public <R extends @Nullable Object> Mut<R> map(Function<? super T, ? extends R> action)
    {
        return of(action.apply(this.get()));
    }

    @Override
    public int hashCode()
    {
        return Objects.hashCode(this.get());
    }
    @Override
    public boolean equals(Object obj)
    {
        if(!(obj instanceof Box))
            return false;
        Box<?> that = (Box<?>) obj;
        return Objects.equals(this.get(), that.get());
    }
    @Override
    public String toString()
    {
        return "(" + this.value + ")";
    }

    @ApiStatus.NonExtendable
    public static class Mut<T extends @Nullable Object> extends Box<T>
    {
        public Mut(T value)
        {
            super(value);
        }

        public void set(T value)
        {
            this.value = value;
        }
    }
}

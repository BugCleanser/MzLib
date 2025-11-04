package mz.mzlib.util;

import java.util.function.Consumer;

public interface ThrowableConsumer<T, E extends Throwable> extends Consumer<T>
{
    void acceptOrThrow(T arg) throws E;
    
    @Override
    default void accept(T t)
    {
        try
        {
            this.acceptOrThrow(t);
        }
        catch(Throwable e)
        {
            throw RuntimeUtil.sneakilyThrow(e);
        }
    }
    
    static <T, E extends Throwable> ThrowableConsumer<T, E> of(ThrowableConsumer<T, E> value)
    {
        return value;
    }
    static <T, E extends Throwable> ThrowableConsumer<T, E> ofConsumer(Consumer<T> value)
    {
        return value::accept;
    }
    @Deprecated
    static <T, E extends Throwable> ThrowableConsumer<T, E> of(Consumer<T> value)
    {
        return ofConsumer(value);
    }
    
    static <T, E extends Throwable> ThrowableConsumer<T, E> nothing()
    {
        return value -> {};
    }
}

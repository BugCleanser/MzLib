package mz.mzlib.util;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public interface ThrowableSupplier<T, E extends Throwable> extends Supplier<T>
{
    T getOrThrow() throws E;
    
    @Override
    default T get()
    {
        try
        {
            return getOrThrow();
        }
        catch(Throwable e)
        {
            throw RuntimeUtil.sneakilyThrow(e);
        }
    }
    
    default <U> ThrowableSupplier<U, E> thenApply(Function<T, U> after)
    {
        return () -> after.apply(getOrThrow());
    }
    default ThrowableRunnable<E> thenAccept(Consumer<T> after)
    {
        return () -> after.accept(getOrThrow());
    }
    
    static <T, E extends Throwable> ThrowableSupplier<T, E> of(ThrowableSupplier<T, E> value)
    {
        return value;
    }
    
    static <T, E extends Throwable> ThrowableSupplier<T, E> of(Supplier<T> value)
    {
        return value::get;
    }
}

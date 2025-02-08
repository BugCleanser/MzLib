package mz.mzlib.util;

import java.util.function.Function;

public interface ThrowableFunction<T, R, E extends Throwable> extends Function<T, R>
{
    R applyOrThrow(T arg) throws E;
    
    @Override
    default R apply(T t)
    {
        try
        {
            return applyOrThrow(t);
        }
        catch(Throwable e)
        {
            throw RuntimeUtil.sneakilyThrow(e);
        }
    }
    
    @SuppressWarnings("NullableProblems")
    @Override
    default <V> ThrowableFunction<T, V, E> andThen(Function<? super R, ? extends V> after)
    {
        return of(Function.super.andThen(after));
    }
    
    static <T, R, E extends Throwable> ThrowableFunction<T, R, E> of(ThrowableFunction<T, R, E> function)
    {
        return function;
    }
    
    static <T, R, E extends Throwable> ThrowableFunction<T, R, E> of(Function<T, R> function)
    {
        return of(function::apply);
    }
}

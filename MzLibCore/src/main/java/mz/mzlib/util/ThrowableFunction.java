package mz.mzlib.util;

import java.util.function.Consumer;
import java.util.function.Function;

public interface ThrowableFunction<T, R, E extends Throwable> extends Function<T, R>
{
    R applyOrThrow(T arg) throws E;
    
    @Override
    default R apply(T t)
    {
        try
        {
            return this.applyOrThrow(t);
        }
        catch(Throwable e)
        {
            throw RuntimeUtil.sneakilyThrow(e);
        }
    }
    
    @Override
    @SuppressWarnings("NullableProblems")
    default <V> ThrowableFunction<T, V, E> andThen(Function<? super R, ? extends V> after)
    {
        return this.thenApply(after);
    }
    
    default <V> ThrowableFunction<T, V, E> thenApply(Function<? super R, ? extends V> after)
    {
        return of(Function.super.andThen(after));
    }
    
    default ThrowableConsumer<T, E> thenAccept(Consumer<? super R> after)
    {
        return t->after.accept(this.apply(t));
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

package mz.mzlib.util;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public interface ThrowableBiFunction<F, S, R, E extends Throwable> extends BiFunction<F, S, R>
{
    R applyOrThrow(F first, S second) throws E;
    
    @Override
    default R apply(F first, S second)
    {
        try
        {
            return this.applyOrThrow(first, second);
        }
        catch(Throwable e)
        {
            throw RuntimeUtil.sneakilyThrow(e);
        }
    }
    
    static <T, F, R, E extends Throwable> ThrowableBiFunction<T, F, R, E> of(ThrowableBiFunction<T, F, R, E> value)
    {
        return value;
    }
    
    static <T, F, R, E extends Throwable> ThrowableBiFunction<T, F, R, E> of(BiFunction<T, F, R> value)
    {
        return value::apply;
    }
    
    default <F1> ThrowableBiFunction<F1, S, R, E> mapFirst(Function<F1, F> function)
    {
        return (f, s)->this.apply(function.apply(f), s);
    }
    
    default <S1> ThrowableBiFunction<F, S1, R, E> mapSecond(Function<S1, S> function)
    {
        return (f, s)->this.apply(f, function.apply(s));
    }
    
    default ThrowableFunction<S, R, E> bindFirst(Supplier<F> supplier)
    {
        return s->this.apply(supplier.get(), s);
    }
    
    default ThrowableFunction<F, R, E> bindSecond(Supplier<S> supplier)
    {
        return f->this.apply(f, supplier.get());
    }
    
    default <V> ThrowableBiFunction<F, S, V, E> thenApply(Function<? super R, ? extends V> after)
    {
        return of(BiFunction.super.andThen(after));
    }
    
    default ThrowableBiConsumer<F, S, E> thenAccept(Consumer<? super R> after)
    {
        return (f, s)->after.accept(this.apply(f, s));
    }
}

package mz.mzlib.util;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

public interface ThrowableBiConsumer<F, S, E extends Throwable> extends BiConsumer<F, S>
{
    void acceptOrThrow(F first, S second) throws E;

    @Override
    default void accept(F first, S second)
    {
        try
        {
            this.acceptOrThrow(first, second);
        }
        catch(Throwable e)
        {
            throw RuntimeUtil.sneakilyThrow(e);
        }
    }

    static <T, F, E extends Throwable> ThrowableBiConsumer<T, F, E> of(ThrowableBiConsumer<T, F, E> value)
    {
        return value;
    }

    static <T, F, E extends Throwable> ThrowableBiConsumer<T, F, E> ofBiConsumer(BiConsumer<T, F> value)
    {
        return value::accept;
    }
    @Deprecated
    static <T, F, E extends Throwable> ThrowableBiConsumer<T, F, E> of(BiConsumer<T, F> value)
    {
        return ofBiConsumer(value);
    }

    default <F1> ThrowableBiConsumer<F1, S, E> mapFirst(Function<F1, F> function)
    {
        return (f, s) -> this.accept(function.apply(f), s);
    }
    default <S1> ThrowableBiConsumer<F, S1, E> mapSecond(Function<S1, S> function)
    {
        return (f, s) -> this.accept(f, function.apply(s));
    }
    default ThrowableConsumer<S, E> bindFirst(Supplier<F> supplier)
    {
        return s -> this.accept(supplier.get(), s);
    }
    default ThrowableConsumer<F, E> bindSecond(Supplier<S> supplier)
    {
        return f -> this.accept(f, supplier.get());
    }
}

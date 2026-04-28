package mz.mzlib.util;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import java.util.function.*;

@ApiStatus.Experimental
public class Functional
{
    public static <T extends @Nullable Object> Supplier<T> constant(T value)
    {
        return () -> value;
    }
    public static <T extends @Nullable Object, R extends @Nullable Object> Function<T, R> constant1(R value)
    {
        return it -> value;
    }
    public static <T1 extends @Nullable Object, T2 extends @Nullable Object, R extends @Nullable Object> BiFunction<T1, T2, R> constant2(R value)
    {
        return (a, b) -> value;
    }
    public static Runnable nothing()
    {
        return () -> {};
    }
    public static <T extends @Nullable Object> Consumer<T> nothing1()
    {
        return it -> {};
    }
    public static <T1 extends @Nullable Object, T2 extends @Nullable Object> BiConsumer<T1, T2> nothing2()
    {
        return (a, b) -> {};
    }

    public static <T extends @Nullable Object, R extends @Nullable Object> Supplier<R> composeSupplier(Supplier<? extends T> a, Function<? super T, ? extends R> b)
    {
        return () -> b.apply(a.get());
    }
    public static <T extends @Nullable Object> Runnable composeRunnable(Supplier<? extends T> a, Consumer<? super T> b)
    {
        return () -> b.accept(a.get());
    }
    public static <T extends @Nullable Object, U extends @Nullable Object, R extends @Nullable Object> Function<T, R> composeFunction(Function<? super T, ? extends U> a, Function<? super U, ? extends R> b)
    {
        return it -> b.apply(a.apply(it));
    }
    public static <T extends @Nullable Object, U extends @Nullable Object> Consumer<T> composeConsumer(Function<? super T, ? extends U> a, Consumer<? super U> b)
    {
        return it -> b.accept(a.apply(it));
    }
}

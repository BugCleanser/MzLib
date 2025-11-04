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
    
    static <T, U, E2 extends Throwable, E extends E2, E1 extends E2> ThrowableSupplier<U, E2> thenApply(ThrowableSupplier<T, E> supplier, ThrowableFunction<? super T, ? extends U, E1> action)
    {
        return ()->action.applyOrThrow(supplier.getOrThrow());
    }
    
    static <T, E2 extends Throwable, E extends E2, E1 extends E2> ThrowableRunnable<E2> thenAccept(ThrowableSupplier<T, E> supplier, ThrowableConsumer<? super T, E1> action)
    {
        return ()->action.acceptOrThrow(supplier.getOrThrow());
    }
    
    default <U> ThrowableSupplier<U, E> thenApply(Function<? super T, ? extends U> action)
    {
        return thenApply(this, ThrowableFunction.ofFunction(action)::applyOrThrow);
    }
    
    default ThrowableRunnable<E> thenAccept(Consumer<? super T> action)
    {
        return thenAccept(this, ThrowableConsumer.ofConsumer(action));
    }
    
    default <U> ThrowableFunction<U, T, E> ignore()
    {
        return u->get();
    }
    
    static <T, E extends Throwable> ThrowableSupplier<T, E> of(ThrowableSupplier<T, E> value)
    {
        return value;
    }
    
    static <T> ThrowableSupplier<T, RuntimeException> ofSupplier(Supplier<T> value)
    {
        return value::get;
    }
    @Deprecated
    static <T> ThrowableSupplier<T, RuntimeException> of(Supplier<T> value)
    {
        return ofSupplier(value);
    }
    
    static <T> ThrowableSupplier<T, RuntimeException> constant(T value)
    {
        return ()->value;
    }
    static <T> ThrowableSupplier<T, RuntimeException> nul()
    {
        return constant(null);
    }
}

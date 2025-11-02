package mz.mzlib.util;

import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

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
    default <V> ThrowableFunction<T, V, E> andThen(Function<? super R, ? extends V> after)
    {
        return this.thenApply(after);
    }
    
    static <T, U, R, E2 extends Throwable, E extends E2, E1 extends E2> ThrowableFunction<T, R, E2> thenApply(ThrowableFunction<T, U, E> f1, ThrowableFunction<? super U, ? extends R, E1> f2)
    {
        return t->f2.applyOrThrow(f1.applyOrThrow(t));
    }
    
    static <T, R, E2 extends Throwable, E extends E2, E1 extends E2> ThrowableConsumer<T, E2> thenAccept(ThrowableFunction<T, R, E> f, ThrowableConsumer<? super R, E1> f1)
    {
        return t->f1.acceptOrThrow(f.applyOrThrow(t));
    }
    
    default <V> ThrowableFunction<T, V, E> thenApply(Function<? super R, ? extends V> after)
    {
        return thenApply(this, of(after));
    }
    
    default ThrowableConsumer<T, E> thenAccept(Consumer<? super R> after)
    {
        return thenAccept(this, ThrowableConsumer.of(after));
    }
    
    static <T, R, E extends Throwable> ThrowableFunction<T, R, E> of(ThrowableFunction<T, R, E> function)
    {
        return function;
    }
    
    static <T, R, E extends Throwable> ThrowableFunction<T, R, E> of(Function<T, R> function)
    {
        return of(function::apply);
    }
    
    static <T extends WrapperObject> ThrowableFunction<? extends WrapperObject, T, RuntimeException> wrapperCast(WrapperFactory<T> factory)
    {
        return of(InvertibleFunction.wrapperCast(WrapperObject.FACTORY, factory));
    }
    @Deprecated
    static <T extends WrapperObject> ThrowableFunction<? extends WrapperObject, T, RuntimeException> wrapperCast(Function<Object, T> creator)
    {
        return of(InvertibleFunction.wrapperCast(WrapperObject::create, creator));
    }
    
    static <T, R, E extends Throwable> ThrowableFunction<T, R, E> switcher(Predicate<T> predicate, ThrowableFunction<? super T,? extends R, ? extends E> f1, ThrowableFunction<? super T,? extends R, ? extends E> f2)
    {
        return t->predicate.test(t)? f1.applyOrThrow(t) : f2.applyOrThrow(t);
    }
    static <T, E extends Throwable> ThrowableFunction<T, T, E> switcher(Predicate<T> predicate, ThrowableFunction<? super T,? extends T, E> action)
    {
        return switcher(predicate, action, identity());
    }
    
    static <T, U, E extends Throwable> ThrowableFunction<Option<T>, Option<U>, E> optionMap(ThrowableFunction<? super T, ? extends U, E> action)
    {
        return o->o.map(action);
    }
    
    static <T, E extends Throwable> ThrowableFunction<T, T, E> identity()
    {
        return of(Function.identity());
    }
}

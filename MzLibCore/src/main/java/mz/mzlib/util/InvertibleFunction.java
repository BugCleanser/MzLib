package mz.mzlib.util;

import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

import java.util.Optional;
import java.util.function.Function;

public class InvertibleFunction<T, U> extends Invertible<InvertibleFunction<U, T>> implements ThrowableFunction<T, U, RuntimeException>
{
    protected Function<? super T, ? extends U> forward;
    protected Function<? super U, ? extends T> backward;
    public InvertibleFunction(Function<? super T, ? extends U> forward, Function<? super U, ? extends T> backward)
    {
        this.forward = forward;
        this.backward = backward;
    }
    
    @Override
    public InvertibleFunction<U, T> invert()
    {
        return new InvertibleFunction<>(backward, forward);
    }

    @Override
    public U applyOrThrow(T t)
    {
        return this.forward.apply(t);
    }
    
    public <V> InvertibleFunction<T, V> thenApply(InvertibleFunction<U, V> after)
    {
        return new InvertibleFunction<>(this.andThen(after), after.inverse().andThen(this.inverse()));
    }
    public <V> InvertibleFunction<T, V> thenApply(Function<? super U, ? extends V> after, Function<? super V, ? extends U> afterInverse)
    {
        return this.thenApply(new InvertibleFunction<>(after, afterInverse));
    }
    
    public static <T, U> InvertibleFunction<T, U> cast()
    {
        return new InvertibleFunction<>(RuntimeUtil::cast, RuntimeUtil::cast);
    }
    
    public <V> InvertibleFunction<T, V> thenCast()
    {
        return this.thenApply(InvertibleFunction.cast());
    }
    
    public static <T> InvertibleFunction<T, Ref<T>> ref()
    {
        return new InvertibleFunction<>(StrongRef::new, Ref::get);
    }
    
    public static <T> InvertibleFunction<T, Option<T>> option()
    {
        return new InvertibleFunction<>(Option::fromNullable, Option::toNullable);
    }
    
    public static <T> InvertibleFunction<T, Optional<T>> optional()
    {
        return new InvertibleFunction<>(Optional::ofNullable, RuntimeUtil::orNull);
    }
    
    public static <T extends WrapperObject> InvertibleFunction<Object, T> wrapper(WrapperFactory<T> factory)
    {
        return new InvertibleFunction<>(factory::create, WrapperObject::getWrapped);
    }
    @Deprecated
    public static <T extends WrapperObject> InvertibleFunction<Object, T> wrapper(Function<Object, T> creator)
    {
        return new InvertibleFunction<>(creator, WrapperObject::getWrapped);
    }
    
    public static <T extends WrapperObject, U extends WrapperObject> InvertibleFunction<T, U> wrapperCast(WrapperFactory<T> factory, WrapperFactory<U> factory1)
    {
        return wrapper(factory).inverse().thenApply(wrapper(factory1));
    }
    @Deprecated
    public static <T extends WrapperObject, U extends WrapperObject> InvertibleFunction<T, U> wrapperCast(Function<Object, T> creator, Function<Object, U> creator1)
    {
        return wrapper(creator).inverse().thenApply(wrapper(creator1));
    }
    
    public static <T> InvertibleFunction<T, T> identity()
    {
        return new InvertibleFunction<>(ThrowableFunction.identity(), ThrowableFunction.identity());
    }
}

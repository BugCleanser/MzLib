package mz.mzlib.util;

import mz.mzlib.util.wrapper.WrapperObject;

import java.util.Optional;
import java.util.function.Function;

public class InvertibleFunction<T, U> extends Invertible<InvertibleFunction<U, T>> implements ThrowableFunction<T, U, RuntimeException>
{
    protected ThrowableFunction<T, U, RuntimeException> forward;
    protected ThrowableFunction<U, T, RuntimeException> backward;
    public InvertibleFunction(ThrowableFunction<T, U, RuntimeException> forward, ThrowableFunction<U, T, RuntimeException> backward)
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
    
    public static <T, U, E extends Throwable> InvertibleFunction<T, U> cast()
    {
        return new InvertibleFunction<>(RuntimeUtil::cast, RuntimeUtil::cast);
    }
    
    public <V> InvertibleFunction<T, V> thenCast()
    {
        return this.thenApply(InvertibleFunction.cast());
    }
    
    public static <T, E extends Throwable> InvertibleFunction<T, Optional<T>> optional()
    {
        return new InvertibleFunction<>(Optional::ofNullable, RuntimeUtil::orNull);
    }
    
    public static <T extends WrapperObject, E extends Throwable> InvertibleFunction<Object, T> wrap(Function<Object, T> creator)
    {
        return new InvertibleFunction<>(ThrowableFunction.of(creator), WrapperObject::getWrapped);
    }
    
    public static <T, E extends Throwable> InvertibleFunction<T, T> identity()
    {
        return new InvertibleFunction<>(ThrowableFunction.identity(), ThrowableFunction.identity());
    }
}

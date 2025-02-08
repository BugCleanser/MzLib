package mz.mzlib.util;

import java.util.function.Function;

public class InvertibleFunction<T, U> implements Function<T, U>
{
    Function<T, U> forward;
    Function<U, T> backward;
    InvertibleFunction<U, T> inverse;

    InvertibleFunction(Function<T, U> forward, Function<U, T> backward, InvertibleFunction<U, T> inverse)
    {
        this.forward = forward;
        this.backward = backward;
        this.inverse = inverse;
    }
    public InvertibleFunction(Function<T, U> forward, Function<U, T> backward)
    {
        this(forward, backward, null);
    }
    
    public InvertibleFunction<U, T> inverse()
    {
        if(this.inverse == null)
            this.inverse = new InvertibleFunction<>(backward, forward, this);
        return this.inverse;
    }

    @Override
    public U apply(T t)
    {
        return this.forward.apply(t);
    }
    
    public static <T> InvertibleFunction<T, T> empty()
    {
        return RuntimeUtil.cast(Empty.instance);
    }
    
    public static <T, U> InvertibleFunction<T, U> cast()
    {
        return new InvertibleFunction<>(RuntimeUtil::cast, RuntimeUtil::cast);
    }
    
    public static class Empty<T> extends InvertibleFunction<T, T>
    {
        public static Empty<?> instance = new Empty<>();
        
        public Empty()
        {
            super(Function.identity(), Function.identity());
        }
    }
}

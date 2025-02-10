package mz.mzlib.util;

import java.util.function.Function;

public class InvertibleFunction<T, U> extends Invertible<InvertibleFunction<U, T>> implements Function<T, U>
{
    protected Function<T, U> forward;
    protected Function<U, T> backward;
    public InvertibleFunction(Function<T, U> forward, Function<U, T> backward)
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

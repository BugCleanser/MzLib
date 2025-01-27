package mz.mzlib.util;

import java.util.function.Function;

public interface ThrowableFunction<T, R, E extends Throwable> extends Function<T, R>
{
    R applyOrThrow(T arg) throws E;
    
    @Override
    default R apply(T t)
    {
        try
        {
            return applyOrThrow(t);
        }
        catch(Throwable e)
        {
            throw RuntimeUtil.sneakilyThrow(e);
        }
    }
}

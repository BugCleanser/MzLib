package mz.mzlib.util;

import java.util.function.Function;

public interface ThrowableFunction<T, R, E extends Throwable>
{
    R apply(T arg) throws E;

    default Function<T, R> toFunction()
    {
        return arg ->
        {
            try
            {
                return this.apply(arg);
            }
            catch (Throwable e)
            {
                throw RuntimeUtil.sneakilyThrow(e);
            }
        };
    }
}

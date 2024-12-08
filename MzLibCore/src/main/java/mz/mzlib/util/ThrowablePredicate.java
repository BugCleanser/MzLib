package mz.mzlib.util;

import java.util.function.Predicate;

public interface ThrowablePredicate<T, E extends Throwable>
{
    boolean test(T arg) throws E;

    default Predicate<T> toPredicate()
    {
        return arg ->
        {
            try
            {
                return this.test(arg);
            }
            catch (Throwable e)
            {
                throw RuntimeUtil.sneakilyThrow(e);
            }
        };
    }
}

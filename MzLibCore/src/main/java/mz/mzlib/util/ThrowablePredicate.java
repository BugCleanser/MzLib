package mz.mzlib.util;

import java.util.function.Predicate;

public interface ThrowablePredicate<T, E extends Throwable> extends Predicate<T>
{
    boolean testOrThrow(T arg) throws E;
    
    @Override
    default boolean test(T t)
    {
        try
        {
            return testOrThrow(t);
        }
        catch(Throwable e)
        {
            throw RuntimeUtil.sneakilyThrow(e);
        }
    }
}

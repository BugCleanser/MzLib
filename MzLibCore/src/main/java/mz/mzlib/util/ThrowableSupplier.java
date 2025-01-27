package mz.mzlib.util;

import java.util.function.Supplier;

public interface ThrowableSupplier<T,E extends Throwable> extends Supplier<T>
{
    T getOrThrow() throws E;

    @Override
    default T get()
    {
        try
        {
            return getOrThrow();
        }
        catch (Throwable e)
        {
            throw RuntimeUtil.sneakilyThrow(e);
        }
    }
}

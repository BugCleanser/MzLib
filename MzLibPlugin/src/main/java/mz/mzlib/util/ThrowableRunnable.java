package mz.mzlib.util;

public interface ThrowableRunnable<E extends Throwable> extends Runnable
{
    void runWithThrowable() throws E;

    @Override
    default void run()
    {
        try
        {
            runWithThrowable();
        }
        catch (Throwable e)
        {
            throw RuntimeUtil.sneakilyThrow(e);
        }
    }
}

package mz.mzlib.util;

public interface ThrowableRunnable<E extends Throwable> extends Runnable
{
    void runOrThrow() throws E;

    @Override
    default void run()
    {
        try
        {
            this.runOrThrow();
        }
        catch (Throwable e)
        {
            throw RuntimeUtil.sneakilyThrow(e);
        }
    }
}

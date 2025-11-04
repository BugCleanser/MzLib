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
    
    static <E extends Throwable> ThrowableRunnable<E> of(ThrowableRunnable<E> value)
    {
        return value;
    }
    static ThrowableRunnable<RuntimeException> ofRunnable(Runnable value)
    {
        return value::run;
    }
}

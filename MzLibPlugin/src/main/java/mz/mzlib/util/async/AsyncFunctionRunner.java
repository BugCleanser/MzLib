package mz.mzlib.util.async;

public interface AsyncFunctionRunner
{
    void schedule(Runnable function);

    void schedule(Runnable function, BasicAwait await);
}

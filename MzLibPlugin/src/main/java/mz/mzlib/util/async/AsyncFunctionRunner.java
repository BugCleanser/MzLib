package mz.mzlib.util.async;

public interface AsyncFunctionRunner
{
	void schedule(AsyncFunction<?> function);
	void schedule(AsyncFunction<?> function,BasicAwait await);
}

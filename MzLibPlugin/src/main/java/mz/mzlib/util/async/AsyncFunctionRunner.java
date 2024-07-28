package mz.mzlib.util.async;

public interface AsyncFunctionRunner
{
	void schedule(AsyncFunction<?> coroutine);
	void schedule(AsyncFunction<?> coroutine,BasicAwait await);
}

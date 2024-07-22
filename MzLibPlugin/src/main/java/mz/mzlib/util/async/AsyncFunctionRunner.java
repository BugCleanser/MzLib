package mz.mzlib.util.async;

public interface AsyncFunctionRunner
{
	void schedule(Coroutine coroutine);
	void schedule(Coroutine coroutine,BasicAwait await);
}

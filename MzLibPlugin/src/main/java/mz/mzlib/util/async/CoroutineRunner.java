package mz.mzlib.util.async;

public interface CoroutineRunner
{
	void schedule(Coroutine coroutine);
	void schedule(Coroutine coroutine,BasicAwait await);
}

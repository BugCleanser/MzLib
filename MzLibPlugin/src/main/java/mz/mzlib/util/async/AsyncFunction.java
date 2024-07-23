package mz.mzlib.util.async;

import mz.mzlib.module.MzModule;
import mz.mzlib.util.RuntimeUtil;

import java.util.concurrent.CompletableFuture;

/**
 * This base class is designed to be extended by public static subclasses with fields all public.
 *
 * @param <R> The return type of the async function
 */
public abstract class AsyncFunction<R>
{
	/**
	 * Extends this constructor
	 * And Take arguments as arguments to the async function
	 */
	public AsyncFunction()
	{
	}
	
	/**
	 * Async function should run only when its module is enabled.
	 */
	public abstract MzModule getModule();
	
	/**
	 * Implement this template method. <br/>
	 * You can use local variables or class fields in your implementation. <br/>
	 * Asynchronous operations can be performed using `this.await(BasicAwait)` or `this.await(CompletableFuture<?>)`. <br/>
	 * **Note:** Do not use the enhanced for-loop (also known as the "for-each" loop) to iterate over any array within this method's implementation. <br/>
	 */
	public abstract R template();
	
	/**
	 * Processed by runner, await a BasicAwait
	 */
	public void await(BasicAwait await)
	{
		throw new UnsupportedOperationException("Must be invoked by async function via 'this'.");
	}
	/**
	 * Await for the completion of a CompletableFuture
	 * If it fails, it will throw an exception, otherwise it will not return a result, and you need to use future.get() to get the result
	 */
	public void await(CompletableFuture<?> future)
	{
		await((BasicAwait)null);
	}
	
	public CoroutineRunner runner;
	public CoroutineRunner getRunner()
	{
		return runner;
	}
	public CompletableFuture<R> start(CoroutineRunner runner)
	{
		this.runner=runner;
		Coroutine coroutine=Coroutine.create(this);
		runner.schedule(coroutine);
		return RuntimeUtil.cast(coroutine.future);
	}
}

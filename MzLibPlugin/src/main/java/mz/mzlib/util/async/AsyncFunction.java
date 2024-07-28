package mz.mzlib.util.async;

import mz.mzlib.module.MzModule;
import mz.mzlib.util.RuntimeUtil;

import java.util.concurrent.CompletableFuture;

/**
 * Directly inherit from this class rather than from its subclasses.
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
	 * Async function should run only when its module is enabled, or else module==null.
	 */
	public abstract MzModule getModule();
	
	/**
	 * Implement this template method. <br/>
	 * You can use local variables or class fields in your implementation. <br/>
	 * Asynchronous operations can be performed using `await(BasicAwait)` or `await(CompletableFuture)`. <br/>
	 * Avoid using 'await' in synchronous code blocks. <br/>
	 */
	public abstract R template();
	
	/**
	 * Processed by runner, await a BasicAwait
	 */
	public static void await(BasicAwait await)
	{
		throw new UnsupportedOperationException("Must be invoked by async function via 'this'.");
	}
	/**
	 * Await for the completion of a CompletableFuture
	 * If it fails, it will throw an exception, otherwise it will not return a result, and you need to use future.get() to get the result
	 */
	public static void await(CompletableFuture<?> future)
	{
		await((BasicAwait)null);
	}
	
	public AsyncFunctionRunner runner;
	public AsyncFunctionRunner getRunner()
	{
		return runner;
	}
	public CompletableFuture<R> start(AsyncFunctionRunner runner)
	{
		this.runner=runner;
		if(this.coroutine==null)
			this.coroutine=Coroutine.init(this);
		runner.schedule(this);
		return RuntimeUtil.cast(coroutine.future);
	}
	
	public Coroutine coroutine;
	/**
	 * Override this method and do nothing. <br/>
	 * Called by AsyncFunctionRunner. <br/>
	 */
	public abstract void run();
	public void run(Object result,Throwable e)
	{
		if(e!=null)
			throw RuntimeUtil.sneakilyThrow(e);
		if(this.getModule()!=null&&!this.getModule().isLoaded)
			return;
		this.run();
	}
}

package mz.mzlib.example;

import mz.mzlib.module.MzModule;
import mz.mzlib.util.async.AsyncFunction;
import mz.mzlib.util.async.CoroutineRunner;
import mz.mzlib.util.async.BasicAwait;
import mz.mzlib.util.async.Coroutine;

public class ExampleAsyncFunction
{
	public static class Function1 extends AsyncFunction<Void>
	{
		public int arg1;
		public Function1(int arg1)
		{
			this.arg1=arg1;
		}
		@Override
		public MzModule getModule()
		{
			return null;
		}
		@Override
		public Void template()
		{
			System.out.println(arg1);
			int i=1;
			System.out.println(i++);
			this.await(new Function2().start(this.getRunner()));
			i++;
			System.out.println(i);
			return null;
		}
	}
	public static class Function2 extends AsyncFunction<Void>
	{
		@Override
		public MzModule getModule()
		{
			return null;
		}
		@Override
		public Void template()
		{
			System.out.println("2");
			return null;
		}
	}
	
	public static void main(String[] args)
	{
		CoroutineRunner runner=new CoroutineRunner()
		{
			@Override
			public void schedule(Coroutine coroutine)
			{
				coroutine.run();
			}
			@Override
			public void schedule(Coroutine coroutine,BasicAwait await)
			{
				throw new UnsupportedOperationException();
			}
		};
		new Function1(114514).start(runner);
	}
}

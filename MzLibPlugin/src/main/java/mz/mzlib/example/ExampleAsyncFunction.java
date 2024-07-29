package mz.mzlib.example;

import mz.mzlib.module.MzModule;
import mz.mzlib.util.async.AsyncFunction;
import mz.mzlib.util.async.AsyncFunctionRunner;
import mz.mzlib.util.async.BasicAwait;

import java.util.function.Consumer;
import java.util.function.Function;

public class ExampleAsyncFunction
{
	public static void main(String[] args)
	{
		AsyncFunctionRunner runner=new AsyncFunctionRunner()
		{
			@Override
			public void schedule(AsyncFunction<?> function)
			{
				function.run();
			}
			@Override
			public void schedule(AsyncFunction<?> function,BasicAwait await)
			{
				throw new UnsupportedOperationException();
			}
		};
		new AsyncFunction<Void>()
		{
			@Override
			public MzModule getModule()
			{
				return null;
			}
			@Override
			public void run()
			{
			}
			@Override
			public Void template()
			{
				String test="aaa";
				Consumer<String> r=System.out::println;
				r.accept(test);
				await(new AsyncFunction<Void>()
				{
					@Override
					public MzModule getModule()
					{
						return null;
					}
					@Override
					public void run()
					{
					}
					@Override
					public Void template()
					{
						System.out.println("test: "+test);
						return null;
					}
				}.start(this.getRunner()));
				Function<String,String> func=s->s+"t";
				String tmp=func.apply(test);
				System.out.println(tmp);
				return null;
			}
		}.start(runner).whenComplete((r,e)->
		{
			if(e!=null)
				e.printStackTrace(System.err);
		});
	}
}

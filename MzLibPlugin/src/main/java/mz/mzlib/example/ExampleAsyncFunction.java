package mz.mzlib.example;

import mz.mzlib.module.MzModule;
import mz.mzlib.util.async.AsyncFunction;
import mz.mzlib.util.async.AsyncFunctionRunner;
import mz.mzlib.util.async.BasicAwait;

import java.util.Arrays;
import java.util.List;

@Deprecated
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
				List<String> l=Arrays.asList("a","b","c");
				for(CharSequence i:l) System.out.println(i);
				return null;
			}
		}.start(runner).whenComplete((r,e)->
		{
			if(e!=null)
				e.printStackTrace(System.err);
		});
	}
}

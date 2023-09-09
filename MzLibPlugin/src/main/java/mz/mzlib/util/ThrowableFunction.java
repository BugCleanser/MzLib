package mz.mzlib.util;

import java.util.function.Function;

public interface ThrowableFunction<T,R> extends Function<T,R>
{
	R applyWithThrowable(T arg) throws Throwable;
	
	@Override
	default R apply(T arg)
	{
		try
		{
			return applyWithThrowable(arg);
		}
		catch(Throwable e)
		{
			throw RuntimeUtil.sneakilyThrow(e);
		}
	}
}

package mz.mzlib.util;

import java.util.function.Predicate;

public interface ThrowablePredicate<T> extends Predicate<T>
{
	boolean applyWithThrowable(T arg) throws Throwable;
	
	@Override
	default boolean test(T arg)
	{
		try
		{
			return applyWithThrowable(arg);
		}
		catch(Throwable e)
		{
			throw RuntimeUtil.forceThrow(e);
		}
	}
}

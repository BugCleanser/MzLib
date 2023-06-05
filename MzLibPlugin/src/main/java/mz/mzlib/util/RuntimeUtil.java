package mz.mzlib.util;

public class RuntimeUtil
{
	private RuntimeUtil() {}
	
	public static final boolean TRUE=true;
	
	@SuppressWarnings("unchecked")
	public static <T> T forceCast(Object object)
	{
		return (T) object;
	}
	public static <T extends Throwable> RuntimeException forceThrow(Throwable e) throws T
	{
		throw RuntimeUtil.<T>forceCast(e);
	}
	@SuppressWarnings("RedundantThrows")
	public static <T extends Throwable> void declareThrow() throws T {}
	@SuppressWarnings("RedundantThrows")
	public static <T extends Throwable> void declareThrow(Class<T> clazz) throws T {}
	public static Throwable runAndCatch(ThrowableRunnable runnable)
	{
		try
		{
			runnable.runWithThrowable();
		}
		catch(Throwable e)
		{
			return e;
		}
		return null;
	}
	public static <T> T require(T object,ThrowablePredicate<T> con)
	{
		assert con.test(object);
		return object;
	}
}

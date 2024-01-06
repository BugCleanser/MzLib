package mz.mzlib.util;

public class RuntimeUtil
{
	private RuntimeUtil() {}
	
	public static boolean TRUE=true;
	
	@SuppressWarnings("unchecked")
	public static <T> T cast(Object object)
	{
		return (T) object;
	}
	public static <T extends Throwable> RuntimeException sneakilyThrow(Throwable e) throws T
	{
		throw RuntimeUtil.<T>cast(e);
	}
	@SuppressWarnings("RedundantThrows")
	public static <T extends Throwable> void declaredlyThrow() throws T {}
	@SuppressWarnings("RedundantThrows")
	public static <T extends Throwable> void declaredlyThrow(Class<T> clazz) throws T {}
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
	public static <T,E extends Throwable> T require(T object,ThrowablePredicate<T,E> con) throws E
	{
		assert con.test(object);
		return object;
	}
}

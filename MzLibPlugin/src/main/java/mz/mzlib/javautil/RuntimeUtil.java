package mz.mzlib.javautil;

public class RuntimeUtil
{
	private RuntimeUtil() {}
	
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
}

package mz.mzlib.module;

public interface IRegistrar<T>
{
	Class<T> getType();
	default boolean isRegistrable(Object object)
	{
		return true;
	}
	void register(MzModule module,T object);
	void unregister(MzModule module,T object);
}

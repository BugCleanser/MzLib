package mz.mzlib.module;

import java.util.HashSet;
import java.util.Set;

public interface IRegistrar<T>
{
	default Set<IRegistrar<?>> getDependencies()
	{
		return new HashSet<>();
	}
	Class<T> getType();
	default boolean isRegistrable(T object)
	{
		return true;
	}
	void register(MzModule module,T object);
	void unregister(MzModule module,T object);
}

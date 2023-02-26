package mz.lib.module;

public interface IRegistrar<T>
{
	Class<T> getType();
	boolean register(MzModule module,T obj);
	void unregister(MzModule module,T obj);
}

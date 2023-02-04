package mz.lib.minecraft.bukkit.module;

import mz.lib.TypeUtil;

import java.util.LinkedList;
import java.util.List;

public interface IRegistrar<T>
{
	List<IRegistrar<?>> registers=new LinkedList<>();
	static <T> List<IRegistrar<T>> getRegistrars(Class<? extends T> type)
	{
		List<IRegistrar<T>> r=new LinkedList<>();
		for(IRegistrar<?> v:registers)
		{
			if(v.getType().isAssignableFrom(type))
			{
				r.add(TypeUtil.cast(v));
			}
		}
		return r;
	}
	
	Class<T> getType();
	default boolean register(IModule module,T obj)
	{
		return register(obj);
	}
	@Deprecated
	default boolean register(T obj)
	{
		throw new UnsupportedOperationException();
	}
	void unregister(T obj);
}

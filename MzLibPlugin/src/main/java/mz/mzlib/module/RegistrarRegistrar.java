package mz.mzlib.module;

import mz.mzlib.util.*;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class RegistrarRegistrar implements IRegistrar<IRegistrar<?>>,Instance
{
	public static RegistrarRegistrar instance=new RegistrarRegistrar();
	
	public final Map<Class<?>,Set<IRegistrar<?>>> registrars=new ConcurrentHashMap<>();
	{
		registrars.put(IRegistrar.class,CollectionUtil.addAll(ConcurrentHashMap.newKeySet(),this));
	}
	
	@Override
	public Class<IRegistrar<?>> getType()
	{
		return RuntimeUtil.cast(IRegistrar.class);
	}
	@Override
	public void register(MzModule module,IRegistrar<?> object)
	{
		synchronized(registrars)
		{
			registrars.computeIfAbsent(object.getType(),t->ConcurrentHashMap.newKeySet()).add(object);
		}
	}
	@Override
	public void unregister(MzModule module,IRegistrar<?> object)
	{
		synchronized(registrars)
		{
			registrars.get(object.getType()).remove(object);
		}
	}
}

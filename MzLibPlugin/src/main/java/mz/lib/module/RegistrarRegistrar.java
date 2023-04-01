package mz.lib.module;

import mz.lib.*;

import java.util.*;
import java.util.concurrent.*;

public class RegistrarRegistrar extends MzModule implements IRegistrar<IRegistrar<?>>
{
	public static RegistrarRegistrar instance=new RegistrarRegistrar();
	public RegistrarRegistrar()
	{
		register(MzLib.instance,this);
	}
	
	public Map<Class<?>,Set<IRegistrar<?>>> registered=new ConcurrentHashMap<>();
	@Override
	public Class<IRegistrar<?>> getType()
	{
		return TypeUtil.cast(IRegistrar.class);
	}
	
	@Override
	public boolean register(MzModule module,IRegistrar<?> obj)
	{
		getRegisteredRegistrars().computeIfAbsent(getType(),i->new ConcurrentHashSet<>()).add(obj);
		return true;
	}
	
	@Override
	public void unregister(MzModule module,IRegistrar<?> obj)
	{
		Set<IRegistrar<?>> r=getRegisteredRegistrars().get(obj.getType());
		r.remove(obj);
		if(r.isEmpty())
			getRegisteredRegistrars().remove(obj.getType());
	}
	
	public Map<Class<?>,Set<IRegistrar<?>>> getRegisteredRegistrars()
	{
		return registered;
	}
}

package mz.mzlib.module;

import mz.mzlib.javautil.*;

import java.util.Map;
import java.util.Set;

public class RegistrarRegistrar implements IRegistrar<IRegistrar<?>>,Instance
{
	public static RegistrarRegistrar instance=new RegistrarRegistrar();
	
	public final Map<Class<?>,Set<IRegistrar<?>>> registrars=new CopyOnWriteMap<>();
	{
		registrars.put(IRegistrar.class,CollectionUtil.addAll(new CopyOnWriteSet<>(),this));
	}
	
	@Override
	public Class<IRegistrar<?>> getType()
	{
		return RuntimeUtil.forceCast(IRegistrar.class);
	}
	@Override
	public void register(MzModule module,IRegistrar<?> object)
	{
		synchronized(registrars)
		{
			registrars.computeIfAbsent(object.getType(),t->new CopyOnWriteSet<>()).add(object);
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

package mz.lib.event;

import mz.lib.*;
import mz.lib.module.*;

import java.util.*;
import java.util.concurrent.*;

public class EventListenerRegistrar implements IRegistrar<IEventListener<?>>
{
	public static EventListenerRegistrar instance=new EventListenerRegistrar();
	Map<Class<? extends Event>,Set<IEventListener<?>>> registered=new ConcurrentHashMap<>();
	
	@Override
	public Class<IEventListener<?>> getType()
	{
		return TypeUtil.cast(IEventListener.class);
	}
	
	@Override
	public boolean register(MzModule module,IEventListener<?> obj)
	{
		registered.computeIfAbsent(obj.getType(),t->Collections.synchronizedSet(new TreeSet<>())).add(obj);
		return true;
	}
	
	@Override
	public void unregister(MzModule module,IEventListener<?> obj)
	{
		registered.get(obj.getType()).remove(obj);
	}
}

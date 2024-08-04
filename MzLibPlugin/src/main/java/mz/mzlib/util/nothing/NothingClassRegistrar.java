package mz.mzlib.util.nothing;

import mz.mzlib.module.IRegistrar;
import mz.mzlib.module.MzModule;
import mz.mzlib.util.RuntimeUtil;
import mz.mzlib.util.delegator.Delegator;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class NothingClassRegistrar implements IRegistrar<Class<? extends Nothing>>
{
	public static NothingClassRegistrar instance=new NothingClassRegistrar();
	
	public Map<Class<?>,NothingRegistration> registrations=new ConcurrentHashMap<>();
	
	@Override
	public Class<Class<? extends Nothing>> getType()
	{
		return RuntimeUtil.cast(Class.class);
	}
	
	@Override
	public boolean isRegistrable(Class<? extends Nothing> object)
	{
		return Nothing.class.isAssignableFrom(object);
	}
	
	@Override
	public void register(MzModule module,Class<? extends Nothing> object)
	{
		if(!Delegator.class.isAssignableFrom(object))
			throw new IllegalArgumentException("Nothing class must implements Delegator.");
		if(!Delegator.class.isInterface())
			throw new IllegalArgumentException("Nothing class must be an interface.");
		Class<?> delegateClass=Delegator.getDelegateClass(RuntimeUtil.<Class<Delegator>>cast(object));
		registrations.computeIfAbsent(delegateClass,k->new NothingRegistration(delegateClass)).add(object);
	}
	
	@Override
	public void unregister(MzModule module,Class<? extends Nothing> object)
	{
		Class<?> delegateClass=Delegator.getDelegateClass(RuntimeUtil.<Class<Delegator>>cast(object));
		registrations.compute(delegateClass,(k,v)->
		{
			if(v==null)
				throw new IllegalArgumentException("Try to unregister a nothing class which has not been registered: "+object);
			v.remove(object);
			if(v.isEmpty())
				return null;
			return v;
		});
	}
}

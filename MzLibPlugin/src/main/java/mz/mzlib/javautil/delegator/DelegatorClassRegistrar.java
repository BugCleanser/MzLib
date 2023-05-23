package mz.mzlib.javautil.delegator;

import mz.mzlib.javautil.*;
import mz.mzlib.module.*;

import java.util.*;

public class DelegatorClassRegistrar implements IRegistrar<Class<? extends Delegator>>, Instance
{
	public static DelegatorClassRegistrar instance=new DelegatorClassRegistrar();
	
	public Map<Class<? extends Delegator>,DelegatorClassRegistration> registrations=new CopyOnWriteMap<>();
	
	@Override
	public Class<Class<? extends Delegator>> getType()
	{
		return RuntimeUtil.forceCast(Class.class);
	}
	@Override
	public boolean isRegistrable(Class<? extends Delegator> object)
	{
		return Delegator.class.isAssignableFrom(object);
	}
	
	@Override
	public void register(MzModule module,Class<? extends Delegator> object)
	{
		registrations.put(object,new DelegatorClassRegistration(object));
	}
	@Override
	public void unregister(MzModule module,Class<? extends Delegator> object)
	{
		registrations.remove(object);
	}
}

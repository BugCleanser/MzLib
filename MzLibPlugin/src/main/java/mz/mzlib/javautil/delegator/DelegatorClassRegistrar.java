package mz.mzlib.javautil.delegator;

import mz.mzlib.javautil.CopyOnWriteMap;
import mz.mzlib.javautil.Instance;
import mz.mzlib.javautil.RuntimeUtil;
import mz.mzlib.module.IRegistrar;
import mz.mzlib.module.MzModule;

import java.lang.invoke.MethodHandle;
import java.util.Map;

public class DelegatorClassRegistrar implements IRegistrar<Class<? extends Delegator>>, Instance
{
	public static DelegatorClassRegistrar instance=new DelegatorClassRegistrar();
	
	public Map<Class<? extends Delegator>,DelegatorClassRegistration> delegatorRegistrations=new CopyOnWriteMap<>();
	public Map<Class<? extends Delegator>,MethodHandle> delegatorConstructors=new CopyOnWriteMap<>();
	
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
	
	}
	@Override
	public void unregister(MzModule module,Class<? extends Delegator> object)
	{
		delegatorRegistrations.remove(object);
		delegatorConstructors.remove(object);
	}
}

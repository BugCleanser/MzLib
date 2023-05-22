package mz.mzlib.javautil.delegator;

import mz.mzlib.javautil.CopyOnWriteMap;
import mz.mzlib.javautil.Instance;
import mz.mzlib.javautil.RuntimeUtil;
import mz.mzlib.module.IRegistrar;
import mz.mzlib.module.MzModule;

import java.lang.invoke.MethodHandle;
import java.util.Map;

public class DelegatorClassRegistrar implements IRegistrar<Class<Delegator>>, Instance
{
	public static DelegatorClassRegistrar instance=new DelegatorClassRegistrar();
	
	public Map<Class<?>,MethodHandle> delegatorConstructors=new CopyOnWriteMap<>();
	
	@Override
	public Class<Class<Delegator>> getType()
	{
		return RuntimeUtil.forceCast(Class.class);
	}
	@Override
	public boolean isRegistrable(Class<Delegator> object)
	{
		return Delegator.class.isAssignableFrom(object);
	}
	
	@Override
	public void register(MzModule module,Class<Delegator> object)
	{
	
	}
	@Override
	public void unregister(MzModule module,Class<Delegator> object)
	{
		
	}
}

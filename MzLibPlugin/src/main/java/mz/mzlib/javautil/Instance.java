package mz.mzlib.javautil;

import mz.mzlib.module.IRegistrar;
import mz.mzlib.module.MzModule;
import mz.mzlib.module.RootModule;

import java.util.Map;

public interface Instance
{
	class InstanceRegistrar implements IRegistrar<Instance>,Instance
	{
		public static InstanceRegistrar instance=new InstanceRegistrar();
		
		Map<Class<? extends Instance>,Instance> instances=new CopyOnWriteMap<>();
		{
			register(RootModule.instance,this);
		}
		
		@Override
		public Class<Instance> getType()
		{
			return Instance.class;
		}
		@Override
		public void register(MzModule module,Instance object)
		{
			instances.put(object.getClass(),object);
		}
		@Override
		public void unregister(MzModule module,Instance object)
		{
			instances.remove(object.getClass(),object);
		}
	}
	static <T extends Instance> T get(Class<T> type)
	{
		return RuntimeUtil.forceCast(InstanceRegistrar.instance.instances.get(type));
	}
}

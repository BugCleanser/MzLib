package mz.mzlib.util;

import mz.mzlib.module.IRegistrar;
import mz.mzlib.module.MzModule;
import mz.mzlib.module.RootModule;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public interface Instance
{
	class InstanceRegistrar implements IRegistrar<Instance>,Instance
	{
		public static InstanceRegistrar instance=new InstanceRegistrar();
		
		Map<Class<? extends Instance>,List<Instance>> instances=new ConcurrentHashMap<>();
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
			ClassUtil.forEachSuperUnique(object.getClass(),c->
			{
				if(Instance.class.isAssignableFrom(c))
					instances.computeIfAbsent(RuntimeUtil.forceCast(c),k->new CopyOnWriteArrayList<>()).add(0,object);
			});
		}
		@Override
		public void unregister(MzModule module,Instance object)
		{
			ClassUtil.forEachSuperUnique(object.getClass(),c->
			{
				if(Instance.class.isAssignableFrom(c))
					instances.computeIfPresent(RuntimeUtil.forceCast(c),(k,v)->
					{
						v.remove(object);
						if(v.isEmpty())
							return null;
						else
							return v;
					});
			});
		}
	}
	static <T extends Instance> T get(Class<T> type)
	{
		return RuntimeUtil.forceCast(InstanceRegistrar.instance.instances.get(type).get(0));
	}
}

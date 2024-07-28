package mz.mzlib.util;

import mz.mzlib.module.IRegistrar;
import mz.mzlib.module.MzModule;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public interface Instance
{
	class InstanceRegistrar implements IRegistrar<Instance>
	{
		public static InstanceRegistrar instance=new InstanceRegistrar();
		Map<Class<? extends Instance>,List<Instance>> instances=new ConcurrentHashMap<>();
		
		@Override
		public Class<Instance> getType()
		{
			return Instance.class;
		}
		public <T extends Instance> void setInstance(Class<T> type,T instance) throws NoSuchFieldException, IllegalAccessException
		{
			try
			{
				ClassUtil.unreflectSetter(type.getDeclaredField("instance")).invoke(null,instance);
			}
			catch(Throwable e)
			{
				throw RuntimeUtil.sneakilyThrow(e);
			}
		}
		@Override
		public void register(MzModule module,Instance object)
		{
			ClassUtil.forEachSuperUnique(object.getClass(),c->
			{
				if(Instance.class.isAssignableFrom(c))
				{
					instances.computeIfAbsent(RuntimeUtil.cast(c),k->new CopyOnWriteArrayList<>()).add(0,object);
					try
					{
						setInstance(RuntimeUtil.cast(c),object);
					}
					catch(NoSuchFieldException|IllegalAccessException e)
					{
						throw RuntimeUtil.sneakilyThrow(e);
					}
				}
			});
		}
		@Override
		public void unregister(MzModule module,Instance object)
		{
			ClassUtil.forEachSuperUnique(object.getClass(),c->
			{
				if(Instance.class.isAssignableFrom(c))
					instances.computeIfPresent(RuntimeUtil.cast(c),(k,v)->
					{
						v.remove(object);
						if(v.isEmpty())
							return null;
						else
						{
							try
							{
								setInstance(RuntimeUtil.cast(k),v.get(0));
							}
							catch(NoSuchFieldException|IllegalAccessException e)
							{
								throw RuntimeUtil.sneakilyThrow(e);
							}
							return v;
						}
					});
			});
		}
	}
}

package mz.mzlib.javautil.delegator;

import mz.mzlib.javautil.ClassUtil;
import mz.mzlib.javautil.Instance;
import mz.mzlib.javautil.RuntimeUtil;
import mz.mzlib.module.IRegistrar;
import mz.mzlib.module.MzModule;

import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class DelegatorClassRegistrar implements IRegistrar<Class<? extends Delegator>>, Instance
{
	public static DelegatorClassRegistrar instance=new DelegatorClassRegistrar();
	
	public Map<Class<? extends Delegator>,DelegatorClassRegistration> registrations=new ConcurrentHashMap<>();
	
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
		DelegatorClassRegistration registration=new DelegatorClassRegistration(object);
		Queue<Class<?>> q=new ArrayDeque<>(Arrays.asList(object.getInterfaces()));
		while(!q.isEmpty())
		{
			Class<?> now=q.poll();
			if(Delegator.class.isAssignableFrom(now))
			{
				DelegatorClassRegistration r=registrations.get(now);
				if(r!=null)
					for(Map.Entry<Method,Member> j:r.delegateMembers.entrySet())
					{
						if(!Modifier.isStatic(j.getValue().getModifiers()))
							registration.put(ClassUtil.getDeclaredMethod(object,j.getKey()),j.getValue());
					}
				else
					Collections.addAll(q,now.getInterfaces());
			}
		}
		
		Integer[] integerArray = {1, 2, 3, 4, 5};
		int[] intArray = Arrays.stream(integerArray)
				.mapToInt(Integer::intValue)
				.toArray();
		
		registrations.put(object,registration);
	}
	@Override
	public void unregister(MzModule module,Class<? extends Delegator> object)
	{
		registrations.remove(object);
	}
}

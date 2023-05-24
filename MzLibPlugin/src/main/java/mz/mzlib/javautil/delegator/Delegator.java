package mz.mzlib.javautil.delegator;

import mz.mzlib.javautil.RuntimeUtil;
import mz.mzlib.javautil.WeakMap;

import java.lang.invoke.MethodHandle;
import java.util.Map;

public interface Delegator
{
	Object getDelegate();
	void setDelegate(Object delegate);
	
	Map<Class<? extends Delegator>,MethodHandle> constructors=new WeakMap<>();
	static <T extends Delegator> T create(Class<T> clazz,Object delegate)
	{
		try
		{
			return RuntimeUtil.forceCast(constructors.computeIfAbsent(clazz,k->
			{
				;
				return null;
			}).invoke(delegate));
		}
		catch(Throwable e)
		{
			throw RuntimeUtil.forceThrow(e);
		}
	}
	static <T extends Delegator> T createStatic(Class<T> clazz)
	{
		return create(clazz,null);
	}
	
	@DelegationMethod("clone")
	Delegator clone0();
}

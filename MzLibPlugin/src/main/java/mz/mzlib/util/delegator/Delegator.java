package mz.mzlib.util.delegator;

import mz.mzlib.util.RuntimeUtil;

public interface Delegator
{
	Object getDelegate();
	void setDelegate(Object delegate);
	
	static <T extends Delegator> T create(Class<T> clazz,Object delegate)
	{
		try
		{
			return RuntimeUtil.forceCast((Object)DelegatorClassInfo.get(clazz).getConstructor().invokeExact((Object)delegate));
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
	
	@DelegatorMethod("clone")
	Delegator clone0();
}

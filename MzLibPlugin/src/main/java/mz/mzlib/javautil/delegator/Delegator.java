package mz.mzlib.javautil.delegator;

import mz.mzlib.javautil.RuntimeUtil;

public interface Delegator
{
	Object getDelegate();
	void setDelegate(Object delegate);
	
	static <T extends Delegator> T create(Class<T> clazz,Object delegate)
	{
		try
		{
			return RuntimeUtil.forceCast(DelegatorClassInfo.get(clazz).getConstructor().invoke(delegate));
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

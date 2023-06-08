package mz.mzlib.util.delegator;

import io.github.karlatemp.unsafeaccessor.Root;
import mz.mzlib.util.RuntimeUtil;

@DelegatorClass(Object.class)
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
	static <T extends Delegator> T allocateInstance(Class<T> type)
	{
		try
		{
			return create(type,Root.getUnsafe().allocateInstance(DelegatorClassInfo.get(type).getDelegateClass()));
		}
		catch(Throwable e)
		{
			throw RuntimeUtil.forceThrow(e);
		}
	}
	default <T extends Delegator> T cast(Class<T> type)
	{
		return create(type,this.getDelegate());
	}
	
	@DelegatorMethod("clone")
	Delegator clone0();
}

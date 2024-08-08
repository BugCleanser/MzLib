package mz.mzlib.util.delegator;

import mz.mzlib.util.RuntimeUtil;

import java.lang.invoke.CallSite;
import java.lang.invoke.ConstantCallSite;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

@DelegatorClass(Object.class)
public interface Delegator
{
	Object getDelegate();
	void setDelegate(Object delegate);
	default void setDelegateFrom(Delegator delegator)
	{
		this.setDelegate(delegator.getDelegate());
	}
	
	/**
	 * @deprecated slow
	 */
	@Deprecated
	static <T extends Delegator> T create(Class<T> type,Object delegate)
	{
		try
		{
			return RuntimeUtil.cast((Delegator)DelegatorClassInfo.get(type).getConstructor().invokeExact((Object)delegate));
		}
		catch(Throwable e)
		{
			throw RuntimeUtil.sneakilyThrow(e);
		}
	}
	static CallSite getConstructorCallSite(MethodHandles.Lookup caller,String invokedName,MethodType invokedType,Class<? extends Delegator> type)
	{
		return new ConstantCallSite(DelegatorClassInfo.get(type).getConstructor().asType(invokedType));
	}
	static Class<?> getDelegateClass(Class<? extends Delegator> type)
	{
		return DelegatorClassInfo.get(type).getDelegateClass();
	}
	
	@DelegatorMethod("clone")
	Delegator clone0();
}

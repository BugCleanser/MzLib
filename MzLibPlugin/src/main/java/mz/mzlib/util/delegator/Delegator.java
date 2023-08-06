package mz.mzlib.util.delegator;

import io.github.karlatemp.unsafeaccessor.Root;
import mz.mzlib.util.RuntimeUtil;

import java.lang.reflect.Member;
import java.util.Arrays;

@DelegatorClass(Object.class)
public interface Delegator
{
	Object getDelegate();
	void setDelegate(Object delegate);
	
	static <T extends Delegator> T create(Class<T> type,Object delegate)
	{
		try
		{
			return RuntimeUtil.forceCast((Object)DelegatorClassInfo.get(type).getConstructor().invokeExact((Object)delegate));
		}
		catch(Throwable e)
		{
			throw RuntimeUtil.forceThrow(e);
		}
	}
	static <T extends Delegator> T createStatic(Class<T> type)
	{
		return create(type,null);
	}
	static Class<?> getDelegateClass(Class<? extends Delegator> type)
	{
		return DelegatorClassInfo.get(type).getDelegateClass();
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
	static Member findMember(Class<?> type,String[] names,Class<?>[] args)
	{
		args=Arrays.copyOf(args,args.length);
		for(int i=0;i<args.length;i++)
			if(Delegator.class.isAssignableFrom(args[i]))
				args[i]=Delegator.getDelegateClass(RuntimeUtil.forceCast(args[i]));
		for(String name:names)
			try
			{
				if("<init>".equals(name))
					return type.getDeclaredConstructor(args);
				else
					return type.getDeclaredMethod(name,args);
			}
			catch(NoSuchMethodException ignored)
			{
			}
		return null;
	}
	default <T extends Delegator> T cast(Class<T> type)
	{
		return create(type,this.getDelegate());
	}
	
	@DelegatorMethod("clone")
	Delegator clone0();
}

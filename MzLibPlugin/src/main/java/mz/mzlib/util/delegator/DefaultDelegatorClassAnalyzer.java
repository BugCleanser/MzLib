package mz.mzlib.util.delegator;

import mz.mzlib.util.Instance;
import mz.mzlib.util.RuntimeUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Member;
import java.util.Arrays;

public class DefaultDelegatorClassAnalyzer implements SimpleDelegatorClassAnalyzer,Instance
{
	public static DefaultDelegatorClassAnalyzer instance=new DefaultDelegatorClassAnalyzer();
	
	@Override
	public Class<?> analyseClass(ClassLoader classLoader,Annotation annotation)
	{
		if(annotation instanceof DelegatorClass)
			return ((DelegatorClass)annotation).value();
		else if(annotation instanceof DelegatorClassForName)
		{
			Throwable lastException=null;
			for(String i:((DelegatorClassForName)annotation).value())
			{
				try
				{
					return Class.forName(i,false,classLoader);
				}
				catch(Throwable e)
				{
					lastException=e;
				}
			}
			throw RuntimeUtil.forceThrow(lastException);
		}
		return null;
	}
	
	@Override
	public Member analyseMember(Class<?> delegateClass,Annotation annotation,Class<?> returnType,Class<?>[] argTypes)
	{
		try
		{
			if(annotation instanceof DelegatorConstructor)
			{
				return delegateClass.getDeclaredConstructor(argTypes);
			}
			else if(annotation instanceof DelegatorMethod)
			{
				Throwable lastException=null;
				for(String i: ((DelegatorMethod)annotation).value())
				{
					try
					{
						return delegateClass.getDeclaredMethod(i,argTypes);
					}
					catch(Throwable e)
					{
						lastException=e;
					}
				}
				throw RuntimeUtil.forceThrow(lastException);
			}
			else if(annotation instanceof DelegatorFieldAccessor)
			{
				switch(argTypes.length)
				{
					case 0:
						//awa
						break;
					case 1:
						break;
					default:
						throw new IllegalArgumentException("Too many args: "+Arrays.toString(argTypes)+".");
				}
			}
			return null;
		}
		catch(Throwable e)
		{
			throw RuntimeUtil.forceThrow(e);
		}
	}
}

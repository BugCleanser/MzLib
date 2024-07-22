package mz.mzlib.util.delegator;

import mz.mzlib.util.RuntimeUtil;

import java.lang.annotation.Annotation;

public class DefaultDelegatorClassFinder
{
	public Class<?> findClass(ClassLoader classLoader,Annotation annotation)
	{
		if(annotation instanceof DelegatorClass)
			return ((DelegatorClass)annotation).value();
		else if(annotation instanceof DelegatorSameClass)
			return DelegatorClassInfo.get(((DelegatorSameClass)annotation).value()).getDelegateClass();
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
			throw RuntimeUtil.sneakilyThrow(lastException);
		}
		return null;
	}
}

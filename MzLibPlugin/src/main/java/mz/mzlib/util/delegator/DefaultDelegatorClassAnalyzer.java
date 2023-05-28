package mz.mzlib.util.delegator;

import mz.mzlib.util.Instance;
import mz.mzlib.util.RuntimeUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Member;

public class DefaultDelegatorClassAnalyzer implements SimpleDelegatorClassAnalyzer,Instance
{
	public static DefaultDelegatorClassAnalyzer instance=new DefaultDelegatorClassAnalyzer();
	
	@Override
	public Class<?> analyseClass(ClassLoader classLoader,Annotation annotation)
	{
		try
		{
			if(annotation instanceof DelegatorClass)
				return ((DelegatorClass)annotation).value();
			else if(annotation instanceof DelegatorClassForName)
				return Class.forName(((DelegatorClassForName)annotation).value(),false,classLoader);
			return null;
		}
		catch(Throwable e)
		{
			throw RuntimeUtil.forceThrow(e);
		}
	}
	
	@Override
	public Member analyseMember(Class<?> delegateClass,Annotation annotation)
	{
		return null;
	}
}

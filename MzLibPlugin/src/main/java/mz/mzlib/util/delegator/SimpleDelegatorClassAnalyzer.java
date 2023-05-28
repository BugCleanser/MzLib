package mz.mzlib.util.delegator;

import mz.mzlib.util.RuntimeUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public interface SimpleDelegatorClassAnalyzer extends DelegatorClassAnalyzer
{
	Class<?> analyseClass(ClassLoader classLoader,Annotation annotation);
	Member analyseMember(Class<?> delegateClass,Annotation annotation,Class<?>[] argTypes);
	
	@Override
	default void analyse(DelegatorClassInfo info)
	{
		Class<?> delegateClass=null;
		for(Annotation i:info.delegatorClass.getDeclaredAnnotations())
		{
			delegateClass=analyseClass(info.delegatorClass.getClassLoader(),i);
			if(delegateClass!=null)
				break;
		}
		if(delegateClass==null)
			throw new IllegalStateException("Multi delegate class: "+info.delegatorClass);
		info.delegateClass=delegateClass;
		for(Method i:info.delegatorClass.getDeclaredMethods())
			if(Modifier.isAbstract(i.getModifiers()))
			{
				Class<?>[] argTypes=i.getParameterTypes();
				for(int j=0;j<argTypes.length;j++)
				{
					if(Delegator.class.isAssignableFrom(argTypes[j]))
						argTypes[j]=DelegatorClassInfo.get(RuntimeUtil.forceCast(argTypes[j])).getDelegateClass();
				}
				for(Annotation j: i.getDeclaredAnnotations())
				{
					Member m=analyseMember(delegateClass,j,argTypes);
					if(m!=null)
						info.delegations.put(i,m);
				}
			}
	}
}

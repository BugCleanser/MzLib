package mz.mzlib.util.delegator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public interface SimpleDelegatorClassAnalyzer extends DelegatorClassAnalyzer
{
	Class<?> analyseClass(Annotation annotation);
	Member analyseMember(Class<?> delegateClass,Annotation annotation);
	
	@Override
	default void analyse(DelegatorClassInfo info)
	{
		Class<?> delegateClass=null;
		for(Annotation i:info.delegatorClass.getDeclaredAnnotations())
		{
			delegateClass=analyseClass(i);
			if(delegateClass!=null)
				break;
		}
		if(delegateClass==null)
			throw new RuntimeException("awa");
		info.delegateClass=delegateClass;
		for(Method i:info.delegatorClass.getDeclaredMethods())
			if(Modifier.isAbstract(i.getModifiers()))
				for(Annotation j:i.getDeclaredAnnotations())
				{
					Member m=analyseMember(delegateClass,j);
					if(m!=null)
						info.delegations.put(i,m);
				}
	}
}

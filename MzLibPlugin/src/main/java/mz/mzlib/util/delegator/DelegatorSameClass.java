package mz.mzlib.util.delegator;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@DelegatorClassFinderClass(DelegatorSameClass.Finder.class)
public @interface DelegatorSameClass
{
	Class<? extends Delegator> value();
	
	class Finder extends DelegatorClassFinder
	{
		@Override
		public Class<?> find(ClassLoader classLoader,Annotation annotation)
		{
			return DelegatorClassInfo.get(((DelegatorSameClass)annotation).value()).getDelegateClass();
		}
	}
}

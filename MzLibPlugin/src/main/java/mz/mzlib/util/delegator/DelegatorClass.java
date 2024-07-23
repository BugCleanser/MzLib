package mz.mzlib.util.delegator;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@DelegatorClassFinderClass(DelegatorClass.Finder.class)
public @interface DelegatorClass
{
	Class<?> value();
	
	class Finder extends DelegatorClassFinder
	{
		public Class<?> find(ClassLoader classLoader,Annotation annotation)
		{
			return ((DelegatorClass)annotation).value();
		}
	}
}

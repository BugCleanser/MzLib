package mz.mzlib.util.delegator;

import java.lang.annotation.*;
import java.lang.reflect.Member;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@DelegatorMemberFinderClass(DelegatorMethod.Finder.class)
public @interface DelegatorMethod
{
	/**
	 * @return Possible names
	 */
	String[] value();
	
	class Finder extends DelegatorMemberFinder
	{
		@Override
		public Member find(Class<?> delegateClass,Annotation annotation,Class<?> returnType,Class<?>[] argTypes) throws NoSuchMethodException
		{
			NoSuchMethodException lastException=null;
			for(String i: ((DelegatorMethod)annotation).value())
			{
				try
				{
					return delegateClass.getDeclaredMethod(i,argTypes);
				}
				catch(NoSuchMethodException e)
				{
					lastException=e;
				}
			}
			if(lastException!=null)
				throw lastException;
			throw new NoSuchMethodException("No such method: "+annotation);
		}
	}
}

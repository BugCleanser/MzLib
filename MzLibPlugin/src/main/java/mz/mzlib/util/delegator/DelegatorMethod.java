package mz.mzlib.util.delegator;

import java.lang.annotation.*;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;


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
			for(String i:((DelegatorMethod)annotation).value())
			{
				try
				{
					if(i.startsWith("$")||i.startsWith("#"))
						return Arrays.stream(delegateClass.getDeclaredMethods()).filter(m->i.startsWith("$")^Modifier.isStatic(m.getModifiers())).toArray(Method[]::new)[Integer.parseInt(i.substring(1))];
					else
						return delegateClass.getDeclaredMethod(i,argTypes);
				}
				catch(ArrayIndexOutOfBoundsException ignored)
				{
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

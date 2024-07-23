package mz.mzlib.util.delegator;

import java.lang.annotation.*;
import java.lang.reflect.Member;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@DelegatorMemberFinderClass(DelegatorConstructor.Finder.class)
public @interface DelegatorConstructor
{
	class Finder extends DelegatorMemberFinder
	{
		@Override
		public Member find(Class<?> delegateClass,Annotation annotation,Class<?> returnType,Class<?>[] argTypes) throws NoSuchMethodException
		{
			return delegateClass.getDeclaredConstructor(argTypes);
		}
	}
}

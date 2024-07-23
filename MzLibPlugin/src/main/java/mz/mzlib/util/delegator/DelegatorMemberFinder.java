package mz.mzlib.util.delegator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Member;

public abstract class DelegatorMemberFinder
{
	public abstract Member find(Class<?> delegateClass,Annotation annotation,Class<?> returnType,Class<?>[] argTypes) throws NoSuchMethodException, NoSuchFieldException;
}

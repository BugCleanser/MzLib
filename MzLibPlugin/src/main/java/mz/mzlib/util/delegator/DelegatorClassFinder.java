package mz.mzlib.util.delegator;

import java.lang.annotation.Annotation;

public abstract class DelegatorClassFinder
{
	public abstract Class<?> find(ClassLoader classLoader,Annotation annotation) throws ClassNotFoundException;
}

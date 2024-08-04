package mz.mzlib.util.delegator;

import mz.mzlib.asm.tree.ClassNode;

import java.lang.annotation.Annotation;

public abstract class DelegatorClassFinder
{
	public abstract Class<?> find(ClassLoader classLoader,Annotation annotation) throws ClassNotFoundException;
	
	public void extra(Annotation annotation,ClassNode cn)
	{
	}
}

package mz.mzlib.util.wrapper;

import mz.mzlib.asm.tree.ClassNode;

import java.lang.annotation.Annotation;

public interface WrappedClassFinder
{
    Class<?> find(Class<? extends WrapperObject> wrapperClass, Annotation annotation) throws ClassNotFoundException;

    default void extra(Annotation annotation, ClassNode cn)
    {
    }
}

package mz.mzlib.util.wrapper;

import mz.mzlib.asm.tree.ClassNode;

import java.lang.annotation.Annotation;

public interface WrappedClassFinder<T extends Annotation>
{
    Class<?> find(Class<? extends WrapperObject> wrapperClass, T annotation) throws ClassNotFoundException;

    default void extra(T annotation, ClassNode cn)
    {
    }
}

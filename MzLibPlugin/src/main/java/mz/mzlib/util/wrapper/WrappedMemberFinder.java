package mz.mzlib.util.wrapper;

import java.lang.annotation.Annotation;
import java.lang.reflect.Member;

public abstract class WrappedMemberFinder
{
    public abstract Member find(Class<?> wrappedClass, Annotation annotation, Class<?> returnType, Class<?>[] argTypes) throws NoSuchMethodException, NoSuchFieldException;
}

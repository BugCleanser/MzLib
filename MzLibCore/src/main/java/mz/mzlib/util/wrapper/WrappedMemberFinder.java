package mz.mzlib.util.wrapper;

import java.lang.annotation.Annotation;
import java.lang.reflect.Member;
import java.lang.reflect.Method;

public interface WrappedMemberFinder<T extends Annotation>
{
    Member find(Class<? extends WrapperObject> wrapperClass, Class<?> wrappedClass, Method wrapperMethod, T annotation, Class<?> returnType, Class<?>[] argTypes) throws NoSuchMethodException, NoSuchFieldException;
}

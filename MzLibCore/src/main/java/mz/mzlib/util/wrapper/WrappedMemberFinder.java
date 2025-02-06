package mz.mzlib.util.wrapper;

import java.lang.annotation.Annotation;
import java.lang.reflect.Member;

public interface WrappedMemberFinder<T extends Annotation>
{
    Member find(Class<?> wrappedClass, T annotation, Class<?> returnType, Class<?>[] argTypes) throws NoSuchMethodException, NoSuchFieldException;
}

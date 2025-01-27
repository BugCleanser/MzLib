package mz.mzlib.util.wrapper;

import java.lang.annotation.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Member;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@WrappedMemberFinderClass(WrapConstructor.Handler.class)
public @interface WrapConstructor
{
    class Handler implements WrappedMemberFinder
    {
        @Override
        public Constructor<?> find(Class<?> wrappedClass, Annotation annotation, Class<?> returnType, Class<?>[] argTypes) throws NoSuchMethodException
        {
            return wrappedClass.getDeclaredConstructor(argTypes);
        }
    }
}

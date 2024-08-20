package mz.mzlib.util.wrapper;

import java.lang.annotation.*;
import java.lang.reflect.Member;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@WrappedMemberFinderClass(WrapConstructor.Finder.class)
public @interface WrapConstructor
{
    class Finder extends WrappedMemberFinder
    {
        @Override
        public Member find(Class<?> wrappedClass, Annotation annotation, Class<?> returnType, Class<?>[] argTypes) throws NoSuchMethodException
        {
            return wrappedClass.getDeclaredConstructor(argTypes);
        }
    }
}

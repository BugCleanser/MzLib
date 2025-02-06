package mz.mzlib.util.wrapper;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Constructor;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@WrappedMemberFinderClass(WrapConstructor.Handler.class)
public @interface WrapConstructor
{
    class Handler implements WrappedMemberFinder<WrapConstructor>
    {
        @Override
        public Constructor<?> find(Class<?> wrappedClass, WrapConstructor annotation, Class<?> returnType, Class<?>[] argTypes) throws NoSuchMethodException
        {
            return wrappedClass.getDeclaredConstructor(argTypes);
        }
    }
}

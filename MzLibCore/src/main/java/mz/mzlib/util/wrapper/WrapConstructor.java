package mz.mzlib.util.wrapper;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@WrappedMemberFinderClass(value = WrapConstructor.Handler.class, inheritable = false)
public @interface WrapConstructor
{
    class Handler implements WrappedMemberFinder<WrapConstructor>
    {
        @Override
        public Constructor<?> find(
            Class<? extends WrapperObject> wrapperClass,
            Class<?> wrappedClass,
            Method wrapperMethod,
            WrapConstructor annotation,
            Class<?> returnType,
            Class<?>[] argTypes) throws NoSuchMethodException
        {
            return wrappedClass.getDeclaredConstructor(argTypes);
        }
    }
}

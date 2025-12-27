package mz.mzlib.util.compound;

import mz.mzlib.util.wrapper.WrappedMemberFinder;
import mz.mzlib.util.wrapper.WrappedMemberFinderClass;
import mz.mzlib.util.wrapper.WrapperObject;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.Arrays;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@WrappedMemberFinderClass(value = PropAccessor.Handler.class, inheritable = false)
public @interface PropAccessor
{
    String value();

    class Handler implements WrappedMemberFinder<PropAccessor>
    {
        @Override
        public Member find(
            Class<? extends WrapperObject> wrapperClass,
            Class<?> wrappedClass,
            Method wrapperMethod,
            PropAccessor annotation,
            Class<?> returnType,
            Class<?>[] argTypes) throws NoSuchFieldException
        {
            Class<?> type;
            switch(argTypes.length)
            {
                case 0:
                    type = returnType;
                    break;
                case 1:
                    type = argTypes[0];
                    break;
                default:
                    throw new IllegalArgumentException("Too many args: " + Arrays.toString(argTypes) + ".");
            }
            Field result = wrappedClass.getDeclaredField(annotation.value());
            if(result.getType() != type)
                throw new IllegalStateException(String.format("Field type(%s) is not prop type(%s)", result.getType(), type));
            return result;
        }
    }
}

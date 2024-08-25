package mz.mzlib.util.compound;

import mz.mzlib.util.RuntimeUtil;
import mz.mzlib.util.wrapper.WrappedMemberFinder;
import mz.mzlib.util.wrapper.WrappedMemberFinderClass;

import java.lang.annotation.*;
import java.lang.reflect.Member;
import java.util.Arrays;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@WrappedMemberFinderClass(PropAccessor.Finder.class)
public @interface PropAccessor
{
    String value();

    class Finder extends WrappedMemberFinder
    {
        @Override
        public Member find(Class<?> wrappedClass, Annotation annotation, Class<?> returnType, Class<?>[] argTypes) throws NoSuchFieldException
        {
            Class<?> type;
            switch (argTypes.length)
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
            return RuntimeUtil.require(wrappedClass.getDeclaredField(((PropAccessor)annotation).value()), f -> f.getType() == type);
        }
    }
}

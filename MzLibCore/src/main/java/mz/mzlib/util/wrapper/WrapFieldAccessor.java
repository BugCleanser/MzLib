package mz.mzlib.util.wrapper;

import mz.mzlib.util.RuntimeUtil;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@WrappedMemberFinderClass(WrapFieldAccessor.Handler.class)
public @interface WrapFieldAccessor
{
    /**
     * @return Possible names
     */
    String[] value();

    class Handler implements WrappedMemberFinder<WrapFieldAccessor>
    {
        @Override
        public Member find(Class<? extends WrapperObject> wrapperClass, Class<?> wrappedClass, Method wrapperMethod, WrapFieldAccessor annotation, Class<?> returnType, Class<?>[] argTypes) throws NoSuchFieldException
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
            for (String i : annotation.value())
            {
                try
                {
                    if (i.startsWith("@") || i.startsWith("#"))
                    {
                        return Arrays.stream(wrappedClass.getDeclaredFields()).filter(j -> j.getType() == type && (Modifier.isStatic(j.getModifiers()) ^ i.startsWith("@"))).toArray(Field[]::new)[Integer.parseInt(i.substring(1))];
                    }
                    else
                    {
                        return RuntimeUtil.require(wrappedClass.getDeclaredField(i), f -> f.getType() == type);
                    }
                }
                catch (AssertionError|ArrayIndexOutOfBoundsException ignored)
                {
                }
            }
            throw new NoSuchFieldException("No such field: " + Arrays.toString(annotation.value()) + ".");
        }
    }
}

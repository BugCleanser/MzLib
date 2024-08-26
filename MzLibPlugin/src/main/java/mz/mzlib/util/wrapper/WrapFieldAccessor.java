package mz.mzlib.util.wrapper;

import mz.mzlib.util.RuntimeUtil;

import java.lang.annotation.*;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
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

    class Handler implements WrappedMemberFinder
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
            NoSuchFieldException lastException = null;
            for (String i : ((WrapFieldAccessor) annotation).value())
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
                catch (AssertionError ignored)
                {
                }
                catch (NoSuchFieldException e)
                {
                    lastException = e;
                }
            }
            if (lastException != null)
            {
                throw lastException;
            }
            else
            {
                throw new NoSuchFieldException("No such field: " + Arrays.toString(((WrapFieldAccessor) annotation).value()) + ".");
            }
        }
    }
}

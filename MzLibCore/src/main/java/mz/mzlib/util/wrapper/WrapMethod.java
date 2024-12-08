package mz.mzlib.util.wrapper;

import java.lang.annotation.*;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@WrappedMemberFinderClass(WrapMethod.Handler.class)
public @interface WrapMethod
{
    /**
     * @return Possible names
     */
    String[] value();

    class Handler implements WrappedMemberFinder
    {
        @Override
        public Member find(Class<?> wrappedClass, Annotation annotation, Class<?> returnType, Class<?>[] argTypes) throws NoSuchMethodException
        {
            NoSuchMethodException lastException = null;
            for (String i : ((WrapMethod) annotation).value())
            {
                try
                {
                    if (i.startsWith("@") || i.startsWith("#"))
                    {
                        return Arrays.stream(wrappedClass.getDeclaredMethods()).filter(m -> i.startsWith("@") ^ Modifier.isStatic(m.getModifiers())).filter(m -> Arrays.equals(m.getParameterTypes(), argTypes)).toArray(Method[]::new)[Integer.parseInt(i.substring(1))];
                    }
                    else
                    {
                        return wrappedClass.getDeclaredMethod(i, argTypes);
                    }
                }
                catch (ArrayIndexOutOfBoundsException ignored)
                {
                }
                catch (NoSuchMethodException e)
                {
                    lastException = e;
                }
            }
            if (lastException != null)
            {
                throw lastException;
            }
            throw new NoSuchMethodException("No such method: " + annotation);
        }
    }
}

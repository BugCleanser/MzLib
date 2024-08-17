package mz.mzlib.util.delegator;

import mz.mzlib.util.RuntimeUtil;

import java.lang.annotation.*;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Modifier;
import java.util.Arrays;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@DelegatorMemberFinderClass(DelegatorFieldAccessor.Finder.class)
public @interface DelegatorFieldAccessor
{
    /**
     * @return Possible names
     */
    String[] value();

    class Finder extends DelegatorMemberFinder
    {
        @Override
        public Member find(Class<?> delegateClass, Annotation annotation, Class<?> returnType, Class<?>[] argTypes) throws NoSuchFieldException
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
            for (String i : ((DelegatorFieldAccessor) annotation).value())
            {
                try
                {
                    if (i.startsWith("@") || i.startsWith("#"))
                    {
                        return Arrays.stream(delegateClass.getDeclaredFields()).filter(j -> j.getType() == type && (Modifier.isStatic(j.getModifiers()) ^ i.startsWith("@"))).toArray(Field[]::new)[Integer.parseInt(i.substring(1))];
                    }
                    else
                    {
                        return RuntimeUtil.require(delegateClass.getDeclaredField(i), f -> f.getType() == type);
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
                throw new NoSuchFieldException("No such field: " + Arrays.toString(((DelegatorFieldAccessor) annotation).value()) + ".");
            }
        }
    }
}

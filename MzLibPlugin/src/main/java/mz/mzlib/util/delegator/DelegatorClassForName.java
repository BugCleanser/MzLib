package mz.mzlib.util.delegator;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@DelegatorClassFinderClass(DelegatorClassForName.Finder.class)
public @interface DelegatorClassForName
{
    String[] value();

    class Finder extends DelegatorClassFinder
    {
        @Override
        public Class<?> find(ClassLoader classLoader, Annotation annotation) throws ClassNotFoundException
        {
            ClassNotFoundException lastException = null;
            for (String i : ((DelegatorClassForName) annotation).value())
            {
                try
                {
                    return Class.forName(i, false, classLoader);
                }
                catch (ClassNotFoundException e)
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
                throw new ClassNotFoundException("No class found: " + annotation);
            }
        }
    }
}

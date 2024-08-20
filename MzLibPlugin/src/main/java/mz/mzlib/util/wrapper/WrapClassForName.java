package mz.mzlib.util.wrapper;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@WrappedClassFinderClass(WrapClassForName.Finder.class)
public @interface WrapClassForName
{
    String[] value();

    class Finder extends WrappedClassFinder
    {
        @Override
        public Class<?> find(ClassLoader classLoader, Annotation annotation) throws ClassNotFoundException
        {
            ClassNotFoundException lastException = null;
            for (String i : ((WrapClassForName) annotation).value())
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

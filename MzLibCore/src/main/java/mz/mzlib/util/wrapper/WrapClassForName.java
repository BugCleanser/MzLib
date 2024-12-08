package mz.mzlib.util.wrapper;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@WrappedClassFinderClass(WrapClassForName.Handler.class)
public @interface WrapClassForName
{
    String[] value();

    class Handler implements WrappedClassFinder
    {
        @Override
        public Class<?> find(Class<? extends WrapperObject> wrapperClass, Annotation annotation) throws ClassNotFoundException
        {
            ClassLoader classLoader = wrapperClass.getClassLoader();
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
                throw lastException;
            return null;
        }
    }
}

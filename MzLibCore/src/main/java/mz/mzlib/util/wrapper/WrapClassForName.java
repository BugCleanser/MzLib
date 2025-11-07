package mz.mzlib.util.wrapper;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@WrappedClassFinderClass(WrapClassForName.Handler.class)
public @interface WrapClassForName
{
    String[] value();

    class Handler implements WrappedClassFinder<WrapClassForName>
    {
        @Override
        public Class<?> find(Class<? extends WrapperObject> wrapperClass, WrapClassForName annotation)
            throws ClassNotFoundException
        {
            ClassLoader classLoader = wrapperClass.getClassLoader();
            ClassNotFoundException lastException = null;
            for(String i : annotation.value())
            {
                try
                {
                    return Class.forName(i, false, classLoader);
                }
                catch(ClassNotFoundException e)
                {
                    lastException = e;
                }
            }
            if(lastException != null)
                throw lastException;
            return null;
        }
    }
}

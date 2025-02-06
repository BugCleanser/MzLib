package mz.mzlib.util.wrapper;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@WrappedClassFinderClass(WrapInnerClass.Handler.class)
public @interface WrapInnerClass
{
    Class<? extends WrapperObject> outer();
    String[] name();

    class Handler implements WrappedClassFinder<WrapInnerClass>
    {
        public Class<?> find(Class<? extends WrapperObject> wrapperClass, WrapInnerClass annotation) throws ClassNotFoundException
        {
            ClassLoader classLoader = wrapperClass.getClassLoader();
            ClassNotFoundException lastException = null;
            Class<?> superClass=WrapperObject.getWrappedClass(annotation.outer());
            if(superClass==null)
                return null;
            String superName=superClass.getName();
            for (String i : annotation.name())
            {
                try
                {
                    return Class.forName(superName+"$"+i, false, classLoader);
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

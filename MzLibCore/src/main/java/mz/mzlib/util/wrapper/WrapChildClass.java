package mz.mzlib.util.wrapper;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@WrappedClassFinderClass(WrapChildClass.Handler.class)
public @interface WrapChildClass
{
    Class<? extends WrapperObject> wrapperSupper();
    String[] name();

    class Handler implements WrappedClassFinder
    {
        public Class<?> find(Class<? extends WrapperObject> wrapperClass, Annotation annotation) throws ClassNotFoundException
        {
            ClassLoader classLoader = wrapperClass.getClassLoader();
            ClassNotFoundException lastException = null;
            Class<?> superClass=WrapperObject.getWrappedClass(((WrapChildClass) annotation).wrapperSupper());
            if(superClass==null)
                return null;
            String superName=superClass.getName();
            for (String i : ((WrapChildClass) annotation).name())
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

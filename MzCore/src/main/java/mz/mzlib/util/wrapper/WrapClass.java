package mz.mzlib.util.wrapper;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@WrappedClassFinderClass(WrapClass.Handler.class)
public @interface WrapClass
{
    Class<?> value();

    class Handler implements WrappedClassFinder
    {
        public Class<?> find(Class<? extends WrapperObject> wrapperClass, Annotation annotation)
        {
            return ((WrapClass) annotation).value();
        }
    }
}

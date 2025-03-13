package mz.mzlib.util.wrapper;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@WrappedClassFinderClass(WrapClass.Handler.class)
public @interface WrapClass
{
    Class<?> value();

    class Handler implements WrappedClassFinder<WrapClass>
    {
        public Class<?> find(Class<? extends WrapperObject> wrapperClass, WrapClass annotation)
        {
            return annotation.value();
        }
    }
}

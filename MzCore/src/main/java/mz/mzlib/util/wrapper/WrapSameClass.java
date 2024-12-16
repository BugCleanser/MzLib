package mz.mzlib.util.wrapper;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@WrappedClassFinderClass(WrapSameClass.Handler.class)
public @interface WrapSameClass
{
    Class<? extends WrapperObject> value();

    class Handler implements WrappedClassFinder
    {
        @Override
        public Class<?> find(Class<? extends WrapperObject> wrapperClass, Annotation annotation)
        {
            return WrapperClassInfo.get(((WrapSameClass) annotation).value()).getWrappedClass();
        }
    }
}

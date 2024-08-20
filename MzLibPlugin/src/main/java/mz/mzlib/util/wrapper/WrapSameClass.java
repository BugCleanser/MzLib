package mz.mzlib.util.wrapper;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@WrappedClassFinderClass(WrapSameClass.Finder.class)
public @interface WrapSameClass
{
    Class<? extends WrapperObject> value();

    class Finder extends WrappedClassFinder
    {
        @Override
        public Class<?> find(ClassLoader classLoader, Annotation annotation)
        {
            return WrapperClassInfo.get(((WrapSameClass) annotation).value()).getWrappedClass();
        }
    }
}

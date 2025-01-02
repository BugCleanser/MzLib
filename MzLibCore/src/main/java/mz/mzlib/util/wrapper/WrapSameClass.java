package mz.mzlib.util.wrapper;

import mz.mzlib.util.ElementSwitcher;
import mz.mzlib.util.ElementSwitcherClass;

import java.lang.annotation.*;
import java.lang.reflect.AnnotatedElement;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@ElementSwitcherClass(WrapSameClass.Handler.class)
@WrappedClassFinderClass(WrapSameClass.Handler.class)
public @interface WrapSameClass
{
    Class<? extends WrapperObject> value();

    class Handler implements ElementSwitcher, WrappedClassFinder
    {
        @Override
        public boolean isEnabled(Annotation annotation, AnnotatedElement element)
        {
            return ElementSwitcher.isEnabled(((WrapSameClass) annotation).value());
        }
        
        @Override
        public Class<?> find(Class<? extends WrapperObject> wrapperClass, Annotation annotation)
        {
            return WrapperClassInfo.get(((WrapSameClass) annotation).value()).getWrappedClass();
        }
    }
}

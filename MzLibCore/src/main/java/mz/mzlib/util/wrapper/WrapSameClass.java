package mz.mzlib.util.wrapper;

import mz.mzlib.util.ElementSwitcher;
import mz.mzlib.util.ElementSwitcherClass;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.AnnotatedElement;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@ElementSwitcherClass(WrapSameClass.Handler.class)
@WrappedClassFinderClass(WrapSameClass.Handler.class)
public @interface WrapSameClass
{
    Class<? extends WrapperObject> value();

    class Handler implements ElementSwitcher<WrapSameClass>, WrappedClassFinder<WrapSameClass>
    {
        @Override
        public boolean isEnabled(WrapSameClass annotation, AnnotatedElement element)
        {
            return ElementSwitcher.isEnabled(((WrapSameClass) annotation).value());
        }
        
        @Override
        public Class<?> find(Class<? extends WrapperObject> wrapperClass, WrapSameClass annotation)
        {
            return WrapperClassInfo.get(annotation.value()).getWrappedClass();
        }
    }
}

package mz.mzlib.minecraft.mzitem;

import mz.mzlib.util.wrapper.WrappedClassFinder;
import mz.mzlib.util.wrapper.WrappedClassFinderClass;
import mz.mzlib.util.wrapper.WrapperClassInfo;
import mz.mzlib.util.wrapper.WrapperObject;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@WrappedClassFinderClass(MzItemClass.Handler.class)
public @interface MzItemClass
{
    class Handler implements WrappedClassFinder<MzItemClass>
    {
        @Override
        public Class<?> find(Class<? extends WrapperObject> wrapperClass, MzItemClass annotation)
        {
            return WrapperClassInfo.get(MzItem.class).getWrappedClass();
        }
    }
}

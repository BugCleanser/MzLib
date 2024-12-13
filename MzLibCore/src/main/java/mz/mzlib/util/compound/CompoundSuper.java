package mz.mzlib.util.compound;

import mz.mzlib.util.ElementSwitcher;
import mz.mzlib.util.ElementSwitcherClass;
import mz.mzlib.util.RuntimeUtil;
import mz.mzlib.util.wrapper.WrappedMemberFinder;
import mz.mzlib.util.wrapper.WrappedMemberFinderClass;
import mz.mzlib.util.wrapper.WrapperObject;

import java.lang.annotation.*;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Member;
import java.lang.reflect.Method;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@ElementSwitcherClass(CompoundSuper.Handler.class)
@WrappedMemberFinderClass(value = CompoundSuper.Handler.class, inheritable = false)
public @interface CompoundSuper
{
    Class<? extends WrapperObject> parent();
    
    /**
     * Name of wrapper method
     */
    String method();
    
    class Handler implements ElementSwitcher, WrappedMemberFinder
    {
        @Override
        public boolean isEnabled(Annotation annotation, AnnotatedElement element)
        {
            try
            {
                CompoundSuper compoundSuper = (CompoundSuper)annotation;
                return ElementSwitcher.isEnabled(compoundSuper.parent().getMethod(compoundSuper.method(), ((Method)element).getParameterTypes()));
            }
            catch(Throwable e)
            {
                throw RuntimeUtil.sneakilyThrow(e);
            }
        }
        
        @Override
        public Member find(Class<?> wrappedClass, Annotation annotation, Class<?> returnType, Class<?>[] argTypes) throws NoSuchMethodException
        {
            return wrappedClass.getDeclaredMethod(getInnerMethodName((CompoundSuper) annotation), argTypes);
        }
        
        public static String getInnerMethodName(CompoundSuper annotation)
        {
            return "super$"+annotation.method()+"$"+annotation.parent().getName().replace('.','$');
        }
    }
}

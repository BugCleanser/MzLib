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
    
    class Handler implements ElementSwitcher<CompoundSuper>, WrappedMemberFinder<CompoundSuper>
    {
        @Override
        public boolean isEnabled(CompoundSuper annotation, AnnotatedElement element)
        {
            try
            {
                return ElementSwitcher.isEnabled(annotation.parent().getMethod(annotation.method(), ((Method)element).getParameterTypes()));
            }
            catch(Throwable e)
            {
                throw RuntimeUtil.sneakilyThrow(e);
            }
        }
        
        @Override
        public Member find(Class<?> wrappedClass, CompoundSuper annotation, Class<?> returnType, Class<?>[] argTypes) throws NoSuchMethodException
        {
            return wrappedClass.getDeclaredMethod(getInnerMethodName(annotation), argTypes);
        }
        
        public static String getInnerMethodName(CompoundSuper annotation)
        {
            return "super$"+annotation.method()+"$"+annotation.parent().getName().replace('.','$');
        }
    }
}

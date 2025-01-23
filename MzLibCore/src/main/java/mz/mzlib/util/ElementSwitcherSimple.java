package mz.mzlib.util;

import java.lang.annotation.*;
import java.lang.reflect.AnnotatedElement;
import java.util.function.Supplier;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.CONSTRUCTOR, ElementType.METHOD, ElementType.FIELD})
@ElementSwitcherClass(ElementSwitcherSimple.Handler.class)
public @interface ElementSwitcherSimple
{
    Class<? extends Supplier<Boolean>> value();
    
    class Handler implements ElementSwitcher
    {
        @Override
        public boolean isEnabled(Annotation annotation, AnnotatedElement element)
        {
            try
            {
                return ((ElementSwitcherSimple)element).value().newInstance().get();
            }
            catch(Throwable e)
            {
                throw RuntimeUtil.sneakilyThrow(e);
            }
        }
    }
}

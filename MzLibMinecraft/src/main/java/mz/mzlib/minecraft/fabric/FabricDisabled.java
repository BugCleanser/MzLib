package mz.mzlib.minecraft.fabric;

import mz.mzlib.util.ElementSwitcher;
import mz.mzlib.util.ElementSwitcherClass;

import java.lang.annotation.*;
import java.lang.reflect.AnnotatedElement;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.CONSTRUCTOR, ElementType.METHOD, ElementType.FIELD})
@ElementSwitcherClass(FabricDisabled.Handler.class)
public @interface FabricDisabled
{
    class Handler implements ElementSwitcher<FabricDisabled>
    {
        @Override
        public boolean isEnabled(FabricDisabled annotation, AnnotatedElement element)
        {
            return !new FabricEnabled.Handler().isEnabled(new FabricEnabled()
            {
                @Override
                public Class<? extends Annotation> annotationType()
                {
                    return FabricEnabled.class;
                }
            }, element);
        }
    }
}

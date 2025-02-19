package mz.mzlib.util;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface ElementSwitcherClass
{
    Class<? extends ElementSwitcher<?>> value();
}

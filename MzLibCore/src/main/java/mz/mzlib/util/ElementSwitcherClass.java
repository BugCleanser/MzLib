package mz.mzlib.util;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ElementSwitcherClass
{
    Class<? extends ElementSwitcher<?>> value();
}

package mz.mzlib.util.compound;

import mz.mzlib.util.wrapper.WrapperObject;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CompoundOverride
{
    Class<? extends WrapperObject> parent();
    
    /**
     * Name of wrapper method
     */
    String method();
}

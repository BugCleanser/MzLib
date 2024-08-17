package mz.mzlib.util.delegator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface DelegatorClassFinderClass
{
    Class<? extends DelegatorClassFinder> value();
}

package mz.mzlib.util.wrapper;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface WrappedMemberFinderClass
{
    Class<? extends WrappedMemberFinder<?>> value();
    boolean inheritable() default true;
}

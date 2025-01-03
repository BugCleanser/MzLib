package mz.mzlib.util.nothing;

import mz.mzlib.Priority;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Repeatable(NothingInjects.class)
public @interface NothingInject
{
    String wrapperMethod();

    float priority() default Priority.NORMAL;

    String locateMethod();

    NothingInjectType type();

    /**
     * Represents how many insns you want to SKIP or CATCH
     * In particular, MAX_VALUE means that you make a try-catch for all subsequent insn.
     */
    String locateMethodEnd() default "";
}

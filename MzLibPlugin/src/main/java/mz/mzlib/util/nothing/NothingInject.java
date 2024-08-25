package mz.mzlib.util.nothing;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Repeatable(NothingInjects.class)
public @interface NothingInject
{
    String wrapperMethod();

    float priority() default 0;

    String locateMethod();

    NothingInjectType type();

    /**
     * Represents how many insns you want to SKIP or CATCH
     * In particular, MAX_VALUE means that you make a try-catch for all subsequent insn.
     */
    int length() default Integer.MAX_VALUE;
}

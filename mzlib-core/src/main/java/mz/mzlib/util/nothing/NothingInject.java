package mz.mzlib.util.nothing;

import mz.mzlib.Priority;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Repeatable(NothingInjects.class)
public @interface NothingInject // FIXME
{
    /**
     * The name of wrapper method of target
     */
    String name() default "";
    
    /**
     * The parameter types of wrapper method of target <br>
     * If unset, find the unique method for name
     */
    Class<?>[] params() default { void.class };
    
    NothingInjectType type();
    
    /**
     * The names of locating methods
     */
    String[] locator() default {};

    float priority() default Priority.NORMAL;
    
    
    /**
     * Similar to <code>name</code>
     */
    @Deprecated
    String wrapperMethodName() default "";
    
    /**
     * Similar to <code>params</code>
     */
    @Deprecated
    Class<?>[] wrapperMethodParams() default { void.class };
    
    /**
     * Similar to <code>locator()[0]</code>
     */
    @Deprecated
    String locateMethod() default "<unset>";
    
    /**
     * Similar to <code>locator()[1]</code>
     * <br>
     * Represents how many insns you want to SKIP or CATCH
     * In particular, MAX_VALUE means that you make a try-catch for all subsequent insn.
     */
    @Deprecated
    String locateMethodEnd() default "<unset>";
}

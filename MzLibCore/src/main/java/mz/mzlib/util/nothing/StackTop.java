package mz.mzlib.util.nothing;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Visit the top of the stack.
 * It is the caught exception when CATCH
 * If you have more than one parameter @StackTop, the first is on top of the stack and the others are below.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface StackTop
{
}

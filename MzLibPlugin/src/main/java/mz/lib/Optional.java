package mz.lib;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Means the marked item is dispensable
 */
@Retention(RUNTIME)
@Target({METHOD,TYPE})
public @interface Optional
{
}

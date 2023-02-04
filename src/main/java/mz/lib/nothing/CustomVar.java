package mz.lib.nothing;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Visit custom variable with a name
 * It will be created when it doesn't exist
 * To interact between injectors
 * Be careful not to use overly simplistic name
 * @see LocalVar
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value=ElementType.PARAMETER)
public @interface CustomVar
{
	String value();
}

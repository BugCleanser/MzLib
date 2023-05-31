package mz.lib.mzlang;

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Target(METHOD)
@Repeatable(NotCallSupers.class)
public @interface NotCallSuper
{
	Class<?> value();
}

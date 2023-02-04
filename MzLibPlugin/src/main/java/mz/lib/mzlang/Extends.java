package mz.lib.mzlang;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

@Retention(RUNTIME)
@Target(TYPE)
@Inherited
public @interface Extends
{
	Class<?> value();
}

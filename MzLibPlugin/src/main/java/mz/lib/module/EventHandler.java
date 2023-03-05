package mz.lib.module;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(value=ElementType.METHOD)
public @interface EventHandler
{
	float order() default 5;
}

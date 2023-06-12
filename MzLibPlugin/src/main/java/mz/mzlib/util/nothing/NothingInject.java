package mz.mzlib.util.nothing;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface NothingInject
{
	String[] methodNames();
	Class<?>[] methodArgs();
	LocatingStep[] locatingSteps();
	int expectedMin() default 1;
	int expectedMax() default Integer.MAX_VALUE;
	NothingInjectType type();
	
	/**
	 * @return Represents how many insns you want to REMOVE or CATCH
	 */
	int extra() default 1;
}

package mz.mzlib.util.nothing;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Repeatable(NothingInjects.class)
public @interface NothingInject
{
	float priority() default 0;
	String[] methodNames();
	Class<?>[] methodArgs();
	LocatingStep[] locatingSteps();
	int expectedMin() default 1;
	int expectedMax() default Integer.MAX_VALUE;
	NothingInjectType type();
	
	/**
	 * @return Represents how many insns you want to SKIP or CATCH
	 */
	int length() default Integer.MAX_VALUE;
}

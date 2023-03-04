package mz.lib.minecraft.command.argparser;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
@Documented
public @interface ArgInfo
{
	String name() default "";
	String[] presets() default {};
	boolean mustPreset() default false;
	String errMsg() default "";
	double min() default Double.NEGATIVE_INFINITY;
	double max() default Double.POSITIVE_INFINITY;
	
	Class<? extends IArgParser> parser() default IArgParser.class;
}

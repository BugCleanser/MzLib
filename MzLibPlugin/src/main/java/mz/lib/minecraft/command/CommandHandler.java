package mz.lib.minecraft.command;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CommandHandler
{
	@Deprecated String effect() default "Deprecated";
	boolean mustOp() default false;
	String permission() default "";
}

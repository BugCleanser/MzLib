package mz.lib.minecraft.bukkit.wrapper;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Target(METHOD)
public @interface WrappedBukkitConstructor
{
	float minVer() default Float.MIN_VALUE;
	float maxVer() default Float.MAX_VALUE;
}

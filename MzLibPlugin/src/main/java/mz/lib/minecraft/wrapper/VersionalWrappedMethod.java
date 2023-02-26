package mz.lib.minecraft.wrapper;

import mz.lib.minecraft.VersionalName;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Target(METHOD)
public @interface VersionalWrappedMethod
{
	VersionalName[] value();
}

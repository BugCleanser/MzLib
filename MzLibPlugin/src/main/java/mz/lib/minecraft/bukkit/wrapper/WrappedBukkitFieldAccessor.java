package mz.lib.minecraft.bukkit.wrapper;

import mz.lib.minecraft.bukkit.VersionName;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Target(METHOD)
public @interface WrappedBukkitFieldAccessor
{
	VersionName[] value();
}

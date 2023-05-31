package mz.lib.minecraft.bukkit.mzlang;

import mz.lib.minecraft.bukkit.VersionName;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Target(METHOD)
public @interface RefactorBukkitSign
{
	VersionName[] value();
}

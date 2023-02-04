package mz.lib.minecraft.bukkit;

public @interface VersionName
{
	float minVer() default Float.MIN_VALUE;
	float maxVer() default Float.MAX_VALUE;
	String[] value();
}

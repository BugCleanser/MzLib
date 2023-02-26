package mz.lib.minecraft;

public @interface VersionalName
{
	float minVer() default Float.MIN_VALUE;
	float maxVer() default Float.MAX_VALUE;
	String[] value();
}

package mz.mzlib.util.nothing;

public @interface LocatingStep
{
	LocatingStepType type();
	int arg();
	int maxDistance() default Integer.MAX_VALUE;
}

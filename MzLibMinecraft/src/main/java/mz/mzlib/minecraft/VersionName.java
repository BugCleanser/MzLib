package mz.mzlib.minecraft;

public @interface VersionName
{
    /**
     * The lower bound (inclusive) of the version range
     */
    int begin() default 0;

    /**
     * The upper bound (exclusive) of the version range
     */
    int end() default Integer.MAX_VALUE;

    String name();

    boolean remap() default true;
}

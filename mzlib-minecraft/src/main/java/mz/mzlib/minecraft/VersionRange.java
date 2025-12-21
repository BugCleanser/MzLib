package mz.mzlib.minecraft;

import mz.mzlib.util.ElementSwitcher;
import mz.mzlib.util.ElementSwitcherClass;

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.AnnotatedElement;

@Retention(RetentionPolicy.RUNTIME)
@ElementSwitcherClass(VersionRange.Handler.class)
@Repeatable(VersionRanges.class)
public @interface VersionRange
{
    /**
     * The lower bound (inclusive) of the version range
     */
    int begin() default 0;

    /**
     * The upper bound (exclusive) of the version range
     */
    int end() default Integer.MAX_VALUE;

    class Handler implements ElementSwitcher<VersionRange>
    {
        public boolean isEnabled(VersionRange annotation, AnnotatedElement element)
        {
            return MinecraftPlatform.instance.inVersion(annotation);
        }
    }
}

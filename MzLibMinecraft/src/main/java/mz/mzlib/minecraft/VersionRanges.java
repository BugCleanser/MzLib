package mz.mzlib.minecraft;

import mz.mzlib.util.ElementSwitcher;
import mz.mzlib.util.ElementSwitcherClass;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.AnnotatedElement;

@Retention(RetentionPolicy.RUNTIME)
@ElementSwitcherClass(VersionRanges.Switcher.class)
public @interface VersionRanges
{
    VersionRange[] value();

    class Switcher implements ElementSwitcher<VersionRanges>
    {
        public boolean isEnabled(VersionRanges annotation, AnnotatedElement element)
        {
            for (VersionRange versionRange : annotation.value())
            {
                if (MinecraftPlatform.instance.inVersion(versionRange))
                    return true;
            }
            return false;
        }
    }
}

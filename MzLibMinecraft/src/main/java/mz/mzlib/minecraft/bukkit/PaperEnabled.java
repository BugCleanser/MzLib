package mz.mzlib.minecraft.bukkit;

import mz.mzlib.minecraft.MinecraftPlatform;
import mz.mzlib.util.ElementSwitcher;
import mz.mzlib.util.ElementSwitcherClass;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.AnnotatedElement;

/**
 * @see MinecraftPlatform.Enabled
 */
@Deprecated
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.CONSTRUCTOR, ElementType.METHOD, ElementType.FIELD})
@ElementSwitcherClass(PaperEnabled.Switcher.class)
public @interface PaperEnabled
{
    class Switcher implements ElementSwitcher<PaperEnabled>
    {
        public boolean isEnabled(PaperEnabled annotation, AnnotatedElement element)
        {
            return MinecraftPlatform.instance instanceof MinecraftPlatformBukkit && MinecraftPlatformBukkit.instance.isPaper;
        }
    }
}

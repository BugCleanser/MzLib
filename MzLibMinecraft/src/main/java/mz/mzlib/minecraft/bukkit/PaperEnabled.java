package mz.mzlib.minecraft.bukkit;

import mz.mzlib.minecraft.MinecraftPlatform;
import mz.mzlib.util.ElementSwitcher;
import mz.mzlib.util.ElementSwitcherClass;

import java.lang.annotation.*;
import java.lang.reflect.AnnotatedElement;

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

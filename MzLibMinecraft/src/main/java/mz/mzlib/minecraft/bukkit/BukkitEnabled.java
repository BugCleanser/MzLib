package mz.mzlib.minecraft.bukkit;

import mz.mzlib.minecraft.MinecraftPlatform;
import mz.mzlib.util.ElementSwitcher;
import mz.mzlib.util.ElementSwitcherClass;

import java.lang.annotation.*;
import java.lang.reflect.AnnotatedElement;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.CONSTRUCTOR, ElementType.METHOD, ElementType.FIELD})
@ElementSwitcherClass(BukkitEnabled.Handler.class)
public @interface BukkitEnabled
{
    class Handler implements ElementSwitcher<BukkitEnabled>
    {
        @Override
        public boolean isEnabled(BukkitEnabled annotation, AnnotatedElement element)
        {
            return MinecraftPlatform.instance instanceof MinecraftPlatformBukkit;
        }
    }
}

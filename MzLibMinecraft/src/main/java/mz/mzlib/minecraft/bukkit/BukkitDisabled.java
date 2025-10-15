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
 * @see MinecraftPlatform.Disabled
 */
@Deprecated
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.CONSTRUCTOR, ElementType.METHOD, ElementType.FIELD})
@ElementSwitcherClass(BukkitDisabled.Handler.class)
public @interface BukkitDisabled
{
    class Handler implements ElementSwitcher<BukkitDisabled>
    {
        @Override
        public boolean isEnabled(BukkitDisabled annotation, AnnotatedElement element)
        {
            return !(MinecraftPlatform.instance instanceof MinecraftPlatformBukkit);
        }
    }
}

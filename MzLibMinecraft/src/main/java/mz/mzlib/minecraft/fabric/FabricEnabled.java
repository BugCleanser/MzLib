package mz.mzlib.minecraft.fabric;

import mz.mzlib.minecraft.MinecraftPlatform;
import mz.mzlib.minecraft.bukkit.MinecraftPlatformBukkit;
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
@ElementSwitcherClass(FabricEnabled.Handler.class)
public @interface FabricEnabled
{
    class Handler implements ElementSwitcher<FabricEnabled>
    {
        @Override
        public boolean isEnabled(FabricEnabled annotation, AnnotatedElement element)
        {
            if(MinecraftPlatform.instance instanceof MinecraftPlatformFabric)
                return true;
            if(MinecraftPlatform.instance instanceof MinecraftPlatformBukkit)
                return ((MinecraftPlatformBukkit)MinecraftPlatform.instance).fabric.isSome();
            return false;
        }
    }
}

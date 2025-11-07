package mz.mzlib.minecraft.bukkit.wrapper;

import mz.mzlib.minecraft.MinecraftPlatform;
import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.bukkit.MinecraftPlatformBukkit;
import mz.mzlib.util.wrapper.WrappedClassFinder;
import mz.mzlib.util.wrapper.WrappedClassFinderClass;
import mz.mzlib.util.wrapper.WrapperObject;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@WrappedClassFinderClass(WrapCraftbukkitClass.Handler.class)
public @interface WrapCraftbukkitClass
{
    VersionName[] value();

    class Handler implements WrappedClassFinder<WrapCraftbukkitClass>
    {
        @Override
        public Class<?> find(Class<? extends WrapperObject> wrapperClass, WrapCraftbukkitClass annotation)
            throws ClassNotFoundException
        {
            for(VersionName name : annotation.value())
            {
                if(MinecraftPlatform.instance.inVersion(name))
                {
                    try
                    {
                        if(name.name().startsWith("OBC."))
                        {
                            return Class.forName("org.bukkit.craftbukkit." +
                                (MinecraftPlatformBukkit.instance.protocolVersion != null ?
                                    MinecraftPlatformBukkit.instance.protocolVersion + '.' :
                                    "") + name.name().substring("OBC.".length()));
                        }
                        else
                        {
                            return Class.forName(name.name());
                        }
                    }
                    catch(ClassNotFoundException ignored)
                    {
                    }
                }
            }
            throw new ClassNotFoundException("No class found: " + annotation);
        }
    }
}

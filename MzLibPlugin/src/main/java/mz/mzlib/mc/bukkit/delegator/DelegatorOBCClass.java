package mz.mzlib.mc.bukkit.delegator;

import mz.mzlib.mc.MinecraftPlatform;
import mz.mzlib.mc.VersionName;
import mz.mzlib.mc.bukkit.MinecraftPlatformBukkit;
import mz.mzlib.util.delegator.DelegatorClassFinder;
import mz.mzlib.util.delegator.DelegatorClassFinderClass;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@DelegatorClassFinderClass(DelegatorOBCClass.Finder.class)
public @interface DelegatorOBCClass
{
    VersionName[] value();

    class Finder extends DelegatorClassFinder
    {
        @Override
        public Class<?> find(ClassLoader classLoader, Annotation annotation) throws ClassNotFoundException
        {
            for (VersionName name : ((DelegatorOBCClass) annotation).value())
            {
                if (MinecraftPlatform.instance.inVersion(name))
                {
                    try
                    {
                        if (name.name().startsWith("OBC."))
                        {
                            return Class.forName("org.bukkit.craftbukkit." + (MinecraftPlatformBukkit.instance.protocolVersion != null ? MinecraftPlatformBukkit.instance.protocolVersion + '.' : "") + name.name().substring("OBC.".length()));
                        }
                        else
                        {
                            return Class.forName(name.name());
                        }
                    }
                    catch (ClassNotFoundException ignored)
                    {
                    }
                }
            }
            throw new ClassNotFoundException("No class found: " + annotation);
        }
    }
}

package mz.mzlib.minecraft.wrapper;

import mz.mzlib.minecraft.MinecraftPlatform;
import mz.mzlib.minecraft.VersionName;
import mz.mzlib.util.wrapper.WrappedClassFinder;
import mz.mzlib.util.wrapper.WrappedClassFinderClass;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@WrappedClassFinderClass(WrapMinecraftClass.Finder.class)
public @interface WrapMinecraftClass
{
    VersionName[] value();

    class Finder extends WrappedClassFinder
    {
        @Override
        public Class<?> find(ClassLoader classLoader, Annotation annotation) throws ClassNotFoundException
        {
            ClassNotFoundException lastException = null;
            for (VersionName name : ((WrapMinecraftClass) annotation).value())
            {
                if (MinecraftPlatform.instance.inVersion(name))
                {
                    try
                    {
                        return Class.forName(MinecraftPlatform.instance.getMappingsY2P().mapClass(name.name()));
                    }
                    catch (ClassNotFoundException e)
                    {
                        lastException = e;
                    }
                }
            }
            throw new ClassNotFoundException("No class found: " + annotation, lastException);
        }
    }
}

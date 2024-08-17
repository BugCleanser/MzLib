package mz.mzlib.mc.delegator;

import mz.mzlib.mc.MinecraftPlatform;
import mz.mzlib.mc.VersionName;
import mz.mzlib.util.delegator.DelegatorClassFinder;
import mz.mzlib.util.delegator.DelegatorClassFinderClass;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@DelegatorClassFinderClass(DelegatorMinecraftClass.Finder.class)
public @interface DelegatorMinecraftClass
{
    VersionName[] value();

    class Finder extends DelegatorClassFinder
    {
        @Override
        public Class<?> find(ClassLoader classLoader, Annotation annotation) throws ClassNotFoundException
        {
            ClassNotFoundException lastException = null;
            for (VersionName name : ((DelegatorMinecraftClass) annotation).value())
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

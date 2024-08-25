package mz.mzlib.minecraft.wrapper;

import mz.mzlib.minecraft.MinecraftPlatform;
import mz.mzlib.minecraft.VersionName;
import mz.mzlib.util.wrapper.WrappedClassFinder;
import mz.mzlib.util.wrapper.WrappedClassFinderClass;
import mz.mzlib.util.wrapper.WrapperObject;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@WrappedClassFinderClass(WrapMinecraftChildClass.Finder.class)
public @interface WrapMinecraftChildClass
{
    Class<? extends WrapperObject> wrapperSupper();
    VersionName[] name();

    class Finder extends WrappedClassFinder
    {
        public Class<?> find(Class<? extends WrapperObject> wrapperClass, Annotation annotation) throws ClassNotFoundException
        {
            ClassLoader classLoader = wrapperClass.getClassLoader();
            Class<?> superClass=WrapperObject.getWrappedClass(((WrapMinecraftChildClass) annotation).wrapperSupper());
            if(superClass==null)
                return null;
            String superName= MinecraftPlatform.instance.getMappingsP2Y().mapClass(superClass.getName());
            ClassNotFoundException lastException = null;
            for (VersionName i : ((WrapMinecraftChildClass) annotation).name())
            {
                if(!MinecraftPlatform.instance.inVersion(i))
                    continue;
                try
                {
                    return Class.forName(MinecraftPlatform.instance.getMappingsY2P().mapClass(superName+"$"+i.name()), false, classLoader);
                }
                catch (ClassNotFoundException e)
                {
                    lastException = e;
                }
            }
            if (lastException != null)
                throw lastException;
            return null;
        }
    }
}

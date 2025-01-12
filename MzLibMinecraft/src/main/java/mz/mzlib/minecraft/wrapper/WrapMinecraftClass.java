package mz.mzlib.minecraft.wrapper;

import mz.mzlib.minecraft.MinecraftPlatform;
import mz.mzlib.minecraft.VersionName;
import mz.mzlib.util.ElementSwitcher;
import mz.mzlib.util.ElementSwitcherClass;
import mz.mzlib.util.wrapper.WrappedClassFinder;
import mz.mzlib.util.wrapper.WrappedClassFinderClass;
import mz.mzlib.util.wrapper.WrapperObject;

import java.lang.annotation.*;
import java.lang.reflect.AnnotatedElement;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@ElementSwitcherClass(WrapMinecraftClass.Handler.class)
@WrappedClassFinderClass(WrapMinecraftClass.Handler.class)
public @interface WrapMinecraftClass
{
    VersionName[] value();
    
    class Handler implements ElementSwitcher, WrappedClassFinder
    {
        @Override
        public boolean isEnabled(Annotation annotation, AnnotatedElement element)
        {
            WrapMinecraftClass a = (WrapMinecraftClass)annotation;
            for(VersionName n: a.value())
            {
                if(MinecraftPlatform.instance.inVersion(n))
                    return true;
            }
            return false;
        }
        
        @Override
        public Class<?> find(Class<? extends WrapperObject> wrapperClass, Annotation annotation) throws ClassNotFoundException
        {
            ClassNotFoundException lastException = null;
            for(VersionName name: ((WrapMinecraftClass)annotation).value())
            {
                if(MinecraftPlatform.instance.inVersion(name))
                {
                    try
                    {
                        return Class.forName(MinecraftPlatform.instance.getMappingsY2P().mapClass(name.name()), false, wrapperClass.getClassLoader());
                    }
                    catch(ClassNotFoundException e)
                    {
                        lastException = e;
                    }
                }
            }
            if(lastException!=null)
                throw new ClassNotFoundException("No class found: "+annotation, lastException);
            return null;
        }
    }
}

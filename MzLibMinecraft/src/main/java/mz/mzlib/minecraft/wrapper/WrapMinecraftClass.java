package mz.mzlib.minecraft.wrapper;

import mz.mzlib.minecraft.MinecraftPlatform;
import mz.mzlib.minecraft.VersionName;
import mz.mzlib.util.ClassUtil;
import mz.mzlib.util.ElementSwitcher;
import mz.mzlib.util.ElementSwitcherClass;
import mz.mzlib.util.Option;
import mz.mzlib.util.wrapper.WrapSameClass;
import mz.mzlib.util.wrapper.WrappedClassFinder;
import mz.mzlib.util.wrapper.WrappedClassFinderClass;
import mz.mzlib.util.wrapper.WrapperObject;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.AnnotatedElement;
import java.util.Arrays;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@ElementSwitcherClass(WrapMinecraftClass.Handler.class)
@WrappedClassFinderClass(WrapMinecraftClass.Handler.class)
public @interface WrapMinecraftClass
{
    VersionName[] value();
    
    class Handler implements ElementSwitcher<WrapMinecraftClass>, WrappedClassFinder<WrapMinecraftClass>
    {
        @Override
        public boolean isEnabled(WrapMinecraftClass annotation, AnnotatedElement element)
        {
            for(VersionName n: annotation.value())
            {
                if(MinecraftPlatform.instance.inVersion(n))
                    return true;
            }
            return false;
        }
        
        public static <T> Option<String> getName(Class<? extends WrapperObject> wrapperClass)
        {
            for(WrapSameClass annotation: Option.fromNullable(wrapperClass.getDeclaredAnnotation(WrapSameClass.class)))
                return getName(annotation.value());
            for(WrapMinecraftClass annotation: Option.fromNullable(wrapperClass.getDeclaredAnnotation(WrapMinecraftClass.class)))
                for(String result: Option.fromOptional(Arrays.stream(annotation.value()).filter(MinecraftPlatform.instance::inVersion).map(VersionName::name).findAny()))
                    return Option.some(result);
            for(WrapMinecraftInnerClass annotation: Option.fromNullable(wrapperClass.getDeclaredAnnotation(WrapMinecraftInnerClass.class)))
                for(Option<String> result: Option.fromOptional(Arrays.stream(annotation.name()).filter(MinecraftPlatform.instance::inVersion).map(n->getName(annotation.outer()).map(o->o+"$"+n.name())).findAny()))
                    return result;
            return Option.none();
        }
        
        @Override
        public Class<?> find(Class<? extends WrapperObject> wrapperClass, WrapMinecraftClass annotation) throws ClassNotFoundException
        {
            ClassNotFoundException lastException = null;
            for(VersionName name: annotation.value())
            {
                if(MinecraftPlatform.instance.inVersion(name))
                {
                    try
                    {
                        return ClassUtil.classForName(name.remap()?MinecraftPlatform.instance.getMappings().inverse().mapClass(name.name()):name.name(), wrapperClass.getClassLoader());
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

package mz.mzlib.minecraft.wrapper;

import mz.mzlib.minecraft.MinecraftPlatform;
import mz.mzlib.minecraft.VersionName;
import mz.mzlib.util.ElementSwitcher;
import mz.mzlib.util.ElementSwitcherClass;
import mz.mzlib.util.RuntimeUtil;
import mz.mzlib.util.wrapper.WrapFieldAccessor;
import mz.mzlib.util.wrapper.WrappedMemberFinder;
import mz.mzlib.util.wrapper.WrappedMemberFinderClass;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Member;
import java.util.Arrays;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@ElementSwitcherClass(WrapMinecraftFieldAccessor.Handler.class)
@WrappedMemberFinderClass(WrapMinecraftFieldAccessor.Handler.class)
public @interface WrapMinecraftFieldAccessor
{
    VersionName[] value();
    
    class Handler implements ElementSwitcher<WrapMinecraftFieldAccessor>, WrappedMemberFinder<WrapMinecraftFieldAccessor>
    {
        @Override
        public boolean isEnabled(WrapMinecraftFieldAccessor annotation, AnnotatedElement element)
        {
            for(VersionName n: annotation.value())
            {
                if(MinecraftPlatform.instance.inVersion(n))
                    return true;
            }
            return false;
        }
        
        @Override
        public Member find(Class<?> wrappedClass, WrapMinecraftFieldAccessor annotation, Class<?> returnType, Class<?>[] argTypes) throws NoSuchFieldException
        {
            String[] names = Arrays.stream(annotation.value()).filter(MinecraftPlatform.instance::inVersion).map(name-> //
                    name.remap()?MinecraftPlatform.instance.getMappings().inverse().mapField(MinecraftPlatform.instance.getMappings().mapClass(wrappedClass.getName()), name.name()):name.name()).toArray(String[]::new);
            if(names.length==0)
                return null;
            try
            {
                return WrapFieldAccessor.Handler.class.newInstance().find(wrappedClass, new WrapFieldAccessor()
                {
                    @Override
                    public Class<WrapFieldAccessor> annotationType()
                    {
                        return WrapFieldAccessor.class;
                    }
                    
                    @Override
                    public String[] value()
                    {
                        return names;
                    }
                }, returnType, argTypes);
            }
            catch(InstantiationException|IllegalAccessException e)
            {
                throw RuntimeUtil.sneakilyThrow(e);
            }
        }
    }
}

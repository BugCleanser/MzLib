package mz.mzlib.minecraft.wrapper;

import mz.mzlib.minecraft.MinecraftPlatform;
import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.mappings.MappingMethod;
import mz.mzlib.util.ElementSwitcher;
import mz.mzlib.util.ElementSwitcherClass;
import mz.mzlib.util.RuntimeUtil;
import mz.mzlib.util.asm.AsmUtil;
import mz.mzlib.util.wrapper.WrapMethod;
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
@ElementSwitcherClass(WrapMinecraftMethod.Handler.class)
@WrappedMemberFinderClass(WrapMinecraftMethod.Handler.class)
public @interface WrapMinecraftMethod
{
    VersionName[] value();
    
    class Handler implements ElementSwitcher<WrapMinecraftMethod>, WrappedMemberFinder<WrapMinecraftMethod>
    {
        @Override
        public boolean isEnabled(WrapMinecraftMethod annotation, AnnotatedElement element)
        {
            for(VersionName n: annotation.value())
            {
                if(MinecraftPlatform.instance.inVersion(n))
                    return true;
            }
            return false;
        }
        
        @Override
        public Member find(Class<?> wrappedClass, WrapMinecraftMethod annotation, Class<?> returnType, Class<?>[] argTypes) throws NoSuchMethodException
        {
            String[] names = Arrays.stream(annotation.value()).filter(MinecraftPlatform.instance::inVersion).map(name-> //
                    name.remap() ? MinecraftPlatform.instance.getMappings().inverse().mapMethod(MinecraftPlatform.instance.getMappings().mapClass(wrappedClass.getName()), new MappingMethod(name.name(), Arrays.stream(argTypes).map(AsmUtil::getDesc).map(MinecraftPlatform.instance.getMappings()::mapType).toArray(String[]::new))) : name.name()).toArray(String[]::new);
            if(names.length==0)
                return null;
            try
            {
                return WrapMethod.Handler.class.newInstance().find(wrappedClass, new WrapMethod()
                {
                    @Override
                    public Class<WrapMethod> annotationType()
                    {
                        return WrapMethod.class;
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

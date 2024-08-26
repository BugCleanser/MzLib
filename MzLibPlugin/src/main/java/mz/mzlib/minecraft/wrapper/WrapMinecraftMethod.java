package mz.mzlib.minecraft.wrapper;

import mz.mzlib.minecraft.mappings.MappingMethod;
import mz.mzlib.minecraft.MinecraftPlatform;
import mz.mzlib.minecraft.VersionName;
import mz.mzlib.util.ElementSwitcher;
import mz.mzlib.util.ElementSwitcherClass;
import mz.mzlib.util.RuntimeUtil;
import mz.mzlib.util.asm.AsmUtil;
import mz.mzlib.util.wrapper.WrappedMemberFinder;
import mz.mzlib.util.wrapper.WrappedMemberFinderClass;
import mz.mzlib.util.wrapper.WrapMethod;

import java.lang.annotation.*;
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

    class Handler implements ElementSwitcher, WrappedMemberFinder
    {
        @Override
        public boolean isEnabled(Annotation annotation, AnnotatedElement element)
        {
            WrapMinecraftMethod a = (WrapMinecraftMethod) annotation;
            for(VersionName n:a.value())
            {
                if (MinecraftPlatform.instance.inVersion(n))
                    return true;
            }
            return false;
        }

        @Override
        public Member find(Class<?> wrappedClass, Annotation annotation, Class<?> returnType, Class<?>[] argTypes) throws NoSuchMethodException
        {
            String[] names = Arrays.stream(((WrapMinecraftMethod) annotation).value()).filter(MinecraftPlatform.instance::inVersion).map(VersionName::name).map(name ->
                    MinecraftPlatform.instance.getMappingsY2P().mapMethod(MinecraftPlatform.instance.getMappingsP2Y().mapClass(wrappedClass.getName()), new MappingMethod(name, Arrays.stream(argTypes).map(AsmUtil::getDesc).map(MinecraftPlatform.instance.getMappingsP2Y()::mapType).toArray(String[]::new)))).toArray(String[]::new);
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
            catch (InstantiationException |
                   IllegalAccessException e)
            {
                throw RuntimeUtil.sneakilyThrow(e);
            }
        }
    }
}

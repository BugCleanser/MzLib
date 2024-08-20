package mz.mzlib.minecraft.wrapper;

import mz.mzlib.mappings.MappingMethod;
import mz.mzlib.minecraft.MinecraftPlatform;
import mz.mzlib.minecraft.VersionName;
import mz.mzlib.util.RuntimeUtil;
import mz.mzlib.util.asm.AsmUtil;
import mz.mzlib.util.wrapper.WrappedMemberFinder;
import mz.mzlib.util.wrapper.WrappedMemberFinderClass;
import mz.mzlib.util.wrapper.WrapMethod;

import java.lang.annotation.*;
import java.lang.reflect.Member;
import java.util.Arrays;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@WrappedMemberFinderClass(WrapMinecraftMethod.Finder.class)
public @interface WrapMinecraftMethod
{
    VersionName[] value();

    class Finder extends WrappedMemberFinder
    {
        @Override
        public Member find(Class<?> wrappedClass, Annotation annotation, Class<?> returnType, Class<?>[] argTypes) throws NoSuchMethodException
        {
            String[] names = Arrays.stream(((WrapMinecraftMethod) annotation).value()).filter(MinecraftPlatform.instance::inVersion).map(VersionName::name).map(name ->
                    MinecraftPlatform.instance.getMappingsY2P().mapMethod(MinecraftPlatform.instance.getMappingsP2Y().mapClass(wrappedClass.getName()), new MappingMethod(name, Arrays.stream(argTypes).map(AsmUtil::getDesc).map(MinecraftPlatform.instance.getMappingsP2Y()::mapClass).toArray(String[]::new)))).toArray(String[]::new);
            try
            {
                return WrapMethod.Finder.class.newInstance().find(wrappedClass, new WrapMethod()
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

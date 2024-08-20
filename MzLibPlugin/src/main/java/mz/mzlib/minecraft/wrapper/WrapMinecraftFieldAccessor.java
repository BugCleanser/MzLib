package mz.mzlib.minecraft.wrapper;

import mz.mzlib.minecraft.MinecraftPlatform;
import mz.mzlib.minecraft.VersionName;
import mz.mzlib.util.RuntimeUtil;
import mz.mzlib.util.wrapper.WrapFieldAccessor;
import mz.mzlib.util.wrapper.WrappedMemberFinder;
import mz.mzlib.util.wrapper.WrappedMemberFinderClass;

import java.lang.annotation.*;
import java.lang.reflect.Member;
import java.util.Arrays;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@WrappedMemberFinderClass(WrapMinecraftFieldAccessor.Finder.class)
public @interface WrapMinecraftFieldAccessor
{
    VersionName[] value();

    class Finder extends WrappedMemberFinder
    {
        @Override
        public Member find(Class<?> wrappedClass, Annotation annotation, Class<?> returnType, Class<?>[] argTypes) throws NoSuchFieldException
        {
            String[] names = Arrays.stream(((WrapMinecraftFieldAccessor) annotation).value()).filter(MinecraftPlatform.instance::inVersion).map(VersionName::name).map(name ->
                    MinecraftPlatform.instance.getMappingsY2P().mapField(MinecraftPlatform.instance.getMappingsP2Y().mapClass(wrappedClass.getName()), name)).toArray(String[]::new);
            try
            {
                return WrapFieldAccessor.Finder.class.newInstance().find(wrappedClass, new WrapFieldAccessor()
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
            catch (InstantiationException |
                   IllegalAccessException e)
            {
                throw RuntimeUtil.sneakilyThrow(e);
            }
        }
    }
}

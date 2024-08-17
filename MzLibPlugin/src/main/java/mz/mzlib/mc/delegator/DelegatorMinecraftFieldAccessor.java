package mz.mzlib.mc.delegator;

import mz.mzlib.mc.MinecraftPlatform;
import mz.mzlib.mc.MinecraftServer;
import mz.mzlib.mc.VersionName;
import mz.mzlib.util.RuntimeUtil;
import mz.mzlib.util.delegator.DelegatorFieldAccessor;
import mz.mzlib.util.delegator.DelegatorMemberFinder;
import mz.mzlib.util.delegator.DelegatorMemberFinderClass;

import java.lang.annotation.*;
import java.lang.reflect.Member;
import java.util.Arrays;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@DelegatorMemberFinderClass(DelegatorMinecraftFieldAccessor.Finder.class)
public @interface DelegatorMinecraftFieldAccessor
{
    VersionName[] value();

    class Finder extends DelegatorMemberFinder
    {
        @Override
        public Member find(Class<?> delegateClass, Annotation annotation, Class<?> returnType, Class<?>[] argTypes) throws NoSuchFieldException
        {
            String[] names = Arrays.stream(((DelegatorMinecraftFieldAccessor) annotation).value()).filter(MinecraftPlatform.instance::inVersion).map(VersionName::name).map(name ->
                    MinecraftPlatform.instance.getMappingsY2P().mapField(MinecraftPlatform.instance.getMappingsP2Y().mapClass(delegateClass.getName()), name)).toArray(String[]::new);
            try
            {
                return DelegatorFieldAccessor.Finder.class.newInstance().find(delegateClass, new DelegatorFieldAccessor()
                {
                    @Override
                    public Class<DelegatorFieldAccessor> annotationType()
                    {
                        return DelegatorFieldAccessor.class;
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

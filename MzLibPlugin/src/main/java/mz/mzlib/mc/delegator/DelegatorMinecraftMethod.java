package mz.mzlib.mc.delegator;

import mz.mzlib.mappings.MappingMethod;
import mz.mzlib.mc.MinecraftPlatform;
import mz.mzlib.mc.VersionName;
import mz.mzlib.util.RuntimeUtil;
import mz.mzlib.util.asm.AsmUtil;
import mz.mzlib.util.delegator.DelegatorMemberFinder;
import mz.mzlib.util.delegator.DelegatorMemberFinderClass;
import mz.mzlib.util.delegator.DelegatorMethod;

import java.lang.annotation.*;
import java.lang.reflect.Member;
import java.util.Arrays;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@DelegatorMemberFinderClass(DelegatorMinecraftMethod.Finder.class)
public @interface DelegatorMinecraftMethod
{
    VersionName[] value();

    class Finder extends DelegatorMemberFinder
    {
        @Override
        public Member find(Class<?> delegateClass, Annotation annotation, Class<?> returnType, Class<?>[] argTypes) throws NoSuchMethodException
        {
            String[] names = Arrays.stream(((DelegatorMinecraftMethod) annotation).value()).filter(MinecraftPlatform.instance::inVersion).map(VersionName::name).map(name ->
                    MinecraftPlatform.instance.getMappingsY2P().mapMethod(MinecraftPlatform.instance.getMappingsP2Y().mapClass(delegateClass.getName()), new MappingMethod(name, Arrays.stream(argTypes).map(AsmUtil::getDesc).map(MinecraftPlatform.instance.getMappingsP2Y()::mapClass).toArray(String[]::new)))).toArray(String[]::new);
            try
            {
                return DelegatorMethod.Finder.class.newInstance().find(delegateClass, new DelegatorMethod()
                {
                    @Override
                    public Class<DelegatorMethod> annotationType()
                    {
                        return DelegatorMethod.class;
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

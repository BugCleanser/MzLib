package mz.mzlib.minecraft.wrapper;

import mz.mzlib.minecraft.MinecraftPlatform;
import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.mappings.MappingMethod;
import mz.mzlib.util.ElementSwitcher;
import mz.mzlib.util.ElementSwitcherClass;
import mz.mzlib.util.Option;
import mz.mzlib.util.RuntimeUtil;
import mz.mzlib.util.asm.AsmUtil;
import mz.mzlib.util.wrapper.WrapMethod;
import mz.mzlib.util.wrapper.WrappedMemberFinder;
import mz.mzlib.util.wrapper.WrappedMemberFinderClass;
import mz.mzlib.util.wrapper.WrapperObject;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
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
        public Member find(Class<? extends WrapperObject> wrapperClass, Class<?> wrappedClass, Method wrapperMethod, WrapMinecraftMethod annotation, Class<?> returnType, Class<?>[] argTypes) throws NoSuchMethodException
        {
            NoSuchMethodException lastException = null;
            l1:
            for(VersionName name: annotation.value())
            {
                if(name.remap())
                {
                    for(String className: WrapMinecraftClass.Handler.getName(wrapperClass))
                    {
                        try
                        {
                            return WrapMethod.Handler.class.newInstance().find(wrapperClass, wrappedClass, wrapperMethod, new WrapMethod()
                            {
                                @Override
                                public Class<WrapMethod> annotationType()
                                {
                                    return WrapMethod.class;
                                }
                                
                                @Override
                                public String[] value()
                                {
                                    return new String[]{MinecraftPlatform.instance.getMappings().inverse().mapMethod(className, new MappingMethod(name.name(), Arrays.stream(wrapperMethod.getParameterTypes()).map(c->
                                    {
                                        if(!WrapperObject.class.isAssignableFrom(c))
                                            return AsmUtil.getDesc(c);
                                        Option<String> name = WrapMinecraftClass.Handler.getName(RuntimeUtil.cast(c));
                                        if(name.isNone())
                                            return AsmUtil.getDesc(WrapperObject.getWrappedClass(RuntimeUtil.cast(c)));
                                        return AsmUtil.getDesc(AsmUtil.getType(name.unwrap()));
                                    }).toArray(String[]::new)))};
                                }
                            }, returnType, argTypes);
                        }
                        catch(NoSuchMethodException e)
                        {
                            lastException = e;
                            continue l1;
                        }
                        catch(InstantiationException|IllegalAccessException e)
                        {
                            throw RuntimeUtil.sneakilyThrow(e);
                        }
                    }
                    lastException = new NoSuchMethodException("Of wrapper "+wrapperMethod);
                }
                else
                {
                    try
                    {
                        return WrapMethod.Handler.class.newInstance().find(wrapperClass, wrappedClass, wrapperMethod, new WrapMethod()
                        {
                            @Override
                            public Class<WrapMethod> annotationType()
                            {
                                return WrapMethod.class;
                            }
                            
                            @Override
                            public String[] value()
                            {
                                return new String[]{name.name()};
                            }
                        }, returnType, argTypes);
                    }
                    catch(NoSuchMethodException e)
                    {
                        lastException = e;
                    }
                    catch(InstantiationException|IllegalAccessException e)
                    {
                        throw RuntimeUtil.sneakilyThrow(e);
                    }
                }
            }
            if(lastException != null)
                throw lastException;
            return null;
        }
    }
}

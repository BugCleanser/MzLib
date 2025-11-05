package mz.mzlib.minecraft.i18n;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.Ref;
import mz.mzlib.util.nothing.LocalVar;
import mz.mzlib.util.nothing.Nothing;
import mz.mzlib.util.nothing.NothingInject;
import mz.mzlib.util.nothing.NothingInjectType;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@VersionRange(end=1300)
@WrapMinecraftClass(@VersionName(name="net.minecraft.util.CommonI18n"))
public interface VanillaI18nV_1300 extends WrapperObject, Nothing
{
    WrapperFactory<VanillaI18nV_1300> FACTORY = WrapperFactory.of(VanillaI18nV_1300.class);
    @Deprecated
    @WrapperCreator
    static VanillaI18nV_1300 create(Object wrapped)
    {
        return WrapperObject.create(VanillaI18nV_1300.class, wrapped);
    }
    
    static String getTranslation(String key)
    {
        return FACTORY.getStatic().staticGetTranslation(key);
    }
    @WrapMinecraftMethod(@VersionName(name="translate"))
    String staticGetTranslation(String key);
    
    ThreadLocal<Ref<String>> lastKey = new ThreadLocal<>();
    @NothingInject(wrapperMethodName="staticGetTranslation", wrapperMethodParams=String.class, locateMethod="", type=NothingInjectType.INSERT_BEFORE)
    static void staticGetTranslationBegin(@LocalVar(0) String key)
    {
        Ref<String> ref = lastKey.get();
        if(ref!=null)
            ref.set(key);
    }
}

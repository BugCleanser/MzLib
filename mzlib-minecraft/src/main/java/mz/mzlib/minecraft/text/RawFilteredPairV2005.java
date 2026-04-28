package mz.mzlib.minecraft.text;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.Option;
import mz.mzlib.util.wrapper.WrapConstructor;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

import java.util.Optional;

@WrapMinecraftClass(@VersionName(name = "net.minecraft.text.RawFilteredPair", begin = 2005))
public interface RawFilteredPairV2005 extends WrapperObject
{
    WrapperFactory<RawFilteredPairV2005> FACTORY = WrapperFactory.of(RawFilteredPairV2005.class);
    static RawFilteredPairV2005 newInstance(WrapperObject raw, Option<? extends WrapperObject> filtered)
    {
        return newInstance0(raw.getWrapped(), Option.toOptional(filtered.mapNullable(WrapperObject::getWrapped)));
    }

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    static <T> RawFilteredPairV2005 newInstance0(T raw, Optional<T> filtered)
    {
        return FACTORY.getStatic().static$newInstance0(raw, filtered);
    }
    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    @WrapConstructor
    <T> RawFilteredPairV2005 static$newInstance0(T raw, Optional<T> filtered);

    @WrapMinecraftMethod(@VersionName(name = "get"))
    Object get0(boolean shouldFilter);

    default <T extends WrapperObject> T get(boolean shouldFilter, WrapperFactory<T> factory)
    {
        return factory.create(this.get0(shouldFilter));
    }
}


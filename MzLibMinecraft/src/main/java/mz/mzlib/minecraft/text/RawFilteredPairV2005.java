package mz.mzlib.minecraft.text;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.util.wrapper.WrapConstructor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

import java.util.Optional;

@WrapMinecraftClass(@VersionName(name="net.minecraft.text.RawFilteredPair", begin=2005))
public interface RawFilteredPairV2005 extends WrapperObject
{
    @WrapperCreator
    static RawFilteredPairV2005 create(Object wrapped)
    {
        return WrapperObject.create(RawFilteredPairV2005.class, wrapped);
    }
    
    static RawFilteredPairV2005 newInstance(WrapperObject raw, WrapperObject filtered)
    {
        return newInstance0(raw.getWrapped(), Optional.ofNullable(filtered!=null?filtered.getWrapped():null));
    }
    
    static <T> RawFilteredPairV2005 newInstance0(T raw, Optional<T> filtered)
    {
        return create(null).staticNewInstance0(raw, filtered);
    }
    @WrapConstructor
    <T> RawFilteredPairV2005 staticNewInstance0(T raw, Optional<T> filtered);
}

package mz.mzlib.minecraft.nbt;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.util.wrapper.*;

@WrapMinecraftClass({@VersionName(name="net.minecraft.nbt.PositionTracker", end=1605), @VersionName(name="net.minecraft.nbt.NbtTagSizeTracker", begin=1605, end=2004), @VersionName(name="net.minecraft.nbt.NbtSizeTracker", begin=2004)})
public interface NbtReadingCounter extends WrapperObject
{
    WrapperFactory<NbtReadingCounter> FACTORY = WrapperFactory.of(NbtReadingCounter.class);
    @Deprecated
    @WrapperCreator
    static NbtReadingCounter create(Object wrapped)
    {
        return WrapperObject.create(NbtReadingCounter.class, wrapped);
    }
    
    int MAX_MAX_DEPTH=512;
    
    static NbtReadingCounter newInstance()
    {
        return newInstance(Long.MAX_VALUE);
    }
    
    NbtReadingCounter staticNewInstance(long maxBytes);
    static NbtReadingCounter newInstance(long maxBytes)
    {
        return create(null).staticNewInstance(maxBytes);
    }
    @SpecificImpl("staticNewInstance")
    @WrapConstructor
    @VersionRange(end=2002)
    NbtReadingCounter staticNewInstanceV_2002(long maxBytes);
    @WrapConstructor
    @VersionRange(begin=2002)
    NbtReadingCounter staticNewInstanceV2002(long maxBytes, int maxDepth);
    @SpecificImpl("staticNewInstance")
    @VersionRange(begin=2002)
    default NbtReadingCounter staticNewInstanceV2002(long maxBytes)
    {
        return this.staticNewInstanceV2002(maxBytes, MAX_MAX_DEPTH);
    }
}

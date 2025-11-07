package mz.mzlib.minecraft.nbt;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.util.wrapper.*;

@WrapMinecraftClass({
    @VersionName(name = "net.minecraft.nbt.PositionTracker", end = 1605),
    @VersionName(name = "net.minecraft.nbt.NbtTagSizeTracker", begin = 1605, end = 2004),
    @VersionName(name = "net.minecraft.nbt.NbtSizeTracker", begin = 2004)
})
public interface NbtReadingCounter extends WrapperObject
{
    WrapperFactory<NbtReadingCounter> FACTORY = WrapperFactory.of(NbtReadingCounter.class);
    @Deprecated
    @WrapperCreator
    static NbtReadingCounter create(Object wrapped)
    {
        return WrapperObject.create(NbtReadingCounter.class, wrapped);
    }

    int MAX_MAX_DEPTH = 512;

    static NbtReadingCounter newInstance()
    {
        return newInstance(Long.MAX_VALUE);
    }

    NbtReadingCounter static$newInstance(long maxBytes);
    static NbtReadingCounter newInstance(long maxBytes)
    {
        return FACTORY.getStatic().static$newInstance(maxBytes);
    }
    @SpecificImpl("static$newInstance")
    @WrapConstructor
    @VersionRange(end = 2002)
    NbtReadingCounter static$newInstanceV_2002(long maxBytes);
    @WrapConstructor
    @VersionRange(begin = 2002)
    NbtReadingCounter static$newInstanceV2002(long maxBytes, int maxDepth);
    @SpecificImpl("static$newInstance")
    @VersionRange(begin = 2002)
    default NbtReadingCounter static$newInstanceV2002(long maxBytes)
    {
        return this.static$newInstanceV2002(maxBytes, MAX_MAX_DEPTH);
    }
}

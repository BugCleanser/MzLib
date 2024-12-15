package mz.mzlib.minecraft.nbt;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.SpecificImpl;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

import java.io.DataInput;

@WrapMinecraftClass({@VersionName(name="net.minecraft.nbt.TagReader", begin=1500, end=1605), @VersionName(name="net.minecraft.nbt.NbtType", begin=1605)})
public interface NbtElementTypeV1500 extends WrapperObject
{
    @WrapperCreator
    static NbtElementTypeV1500 create(Object wrapped)
    {
        return WrapperObject.create(NbtElementTypeV1500.class, wrapped);
    }
    
    NbtElement load(DataInput input, NbtReadingCounter counter);
    @WrapMinecraftMethod(@VersionName(name="read", end=2002))
    NbtElement loadV_2002(DataInput input, int maxDepth, NbtReadingCounter counter);
    @SpecificImpl("load")
    @VersionRange(end=2002)
    default NbtElement loadV_2002(DataInput input, NbtReadingCounter counter)
    {
        return loadV_2002(input, NbtReadingCounter.MAX_MAX_DEPTH, counter);
    }
    @SpecificImpl("load")
    @WrapMinecraftMethod(@VersionName(name="read", begin=2002))
    NbtElement loadV2002(DataInput input, NbtReadingCounter counter);
}

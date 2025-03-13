package mz.mzlib.minecraft.nbt;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.SpecificImpl;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

import java.io.DataInput;

@WrapMinecraftClass({@VersionName(name="net.minecraft.nbt.TagReader", begin=1500, end=1605), @VersionName(name="net.minecraft.nbt.NbtType", begin=1605)})
public interface NbtElementTypeV1500 extends WrapperObject
{
    WrapperFactory<NbtElementTypeV1500> FACTORY = WrapperFactory.find(NbtElementTypeV1500.class);
    @Deprecated
    @WrapperCreator
    static NbtElementTypeV1500 create(Object wrapped)
    {
        return WrapperObject.create(NbtElementTypeV1500.class, wrapped);
    }
    
    NbtElement load(DataInput input, NbtReadingCounter counter);
    @WrapMinecraftMethod(@VersionName(name="read", end=2002))
    NbtElement loadV_2002(DataInput input, int depth, NbtReadingCounter counter);
    @SpecificImpl("load")
    @VersionRange(end=2002)
    default NbtElement loadV_2002(DataInput input, NbtReadingCounter counter)
    {
        return loadV_2002(input, 0, counter);
    }
    @SpecificImpl("load")
    @WrapMinecraftMethod(@VersionName(name="read", begin=2002))
    NbtElement loadV2002(DataInput input, NbtReadingCounter counter);
}

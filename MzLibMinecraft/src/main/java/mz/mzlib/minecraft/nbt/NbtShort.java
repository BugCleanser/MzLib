package mz.mzlib.minecraft.nbt;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.util.wrapper.WrapConstructor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass({@VersionName(end = 1400, name = "net.minecraft.nbt.NbtShort"), @VersionName(begin = 1400, end = 1605, name = "net.minecraft.nbt.ShortTag"), @VersionName(begin = 1605, name = "net.minecraft.nbt.NbtShort")})
public interface NbtShort extends NbtElement
{
    WrapperFactory<NbtShort> FACTORY = WrapperFactory.of(NbtShort.class);
    @Deprecated
    @WrapperCreator
    static NbtShort create(Object wrapped)
    {
        return WrapperObject.create(NbtShort.class, wrapped);
    }

    @WrapConstructor
    NbtShort static$newInstance(short value);
    static NbtShort newInstance(short value)
    {
        return FACTORY.getStatic().static$newInstance(value);
    }

    @WrapMinecraftFieldAccessor({@VersionName(name="value", end=2105), @VersionName(name="comp_3822", begin=2105)})
    short getValue();
    
    @Deprecated
    @WrapMinecraftFieldAccessor({@VersionName(name="value", end=2105), @VersionName(name="comp_3822", begin=2105)})
    void setValue(short value);
}

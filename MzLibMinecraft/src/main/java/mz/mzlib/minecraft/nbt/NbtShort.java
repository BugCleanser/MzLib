package mz.mzlib.minecraft.nbt;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.util.wrapper.WrapConstructor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass({@VersionName(end = 1400, name = "net.minecraft.nbt.NbtShort"), @VersionName(begin = 1400, end = 1605, name = "net.minecraft.nbt.ShortTag"), @VersionName(begin = 1605, name = "net.minecraft.nbt.NbtShort")})
public interface NbtShort extends NbtElement
{
    @WrapperCreator
    static NbtShort create(Object wrapped)
    {
        return WrapperObject.create(NbtShort.class, wrapped);
    }

    @WrapConstructor
    NbtShort staticNewInstance(short value);
    static NbtShort newInstance(short value)
    {
        return create(null).staticNewInstance(value);
    }

    @WrapMinecraftFieldAccessor(@VersionName(name = "value"))
    short getValue();
    @WrapMinecraftFieldAccessor(@VersionName(name = "value"))
    void setValue(short value);
}

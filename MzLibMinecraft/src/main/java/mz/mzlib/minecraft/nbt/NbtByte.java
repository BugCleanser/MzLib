package mz.mzlib.minecraft.nbt;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.util.wrapper.WrapConstructor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass({@VersionName(end = 1400, name = "net.minecraft.nbt.NbtByte"), @VersionName(begin = 1400, end = 1605, name = "net.minecraft.nbt.ByteTag"), @VersionName(begin = 1605, name = "net.minecraft.nbt.NbtByte")})
public interface NbtByte extends NbtElement
{
    @WrapperCreator
    static NbtByte create(Object wrapped)
    {
        return WrapperObject.create(NbtByte.class, wrapped);
    }

    @WrapConstructor
    NbtByte staticNewInstance(byte value);

    static NbtByte newInstance(byte value)
    {
        return create(null).staticNewInstance(value);
    }

    @WrapMinecraftFieldAccessor(@VersionName(name = "value"))
    byte getValue();

    @WrapMinecraftFieldAccessor(@VersionName(name = "value"))
    void setValue(byte value);
}

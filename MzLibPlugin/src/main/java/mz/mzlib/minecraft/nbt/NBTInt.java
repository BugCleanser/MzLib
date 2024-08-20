package mz.mzlib.minecraft.nbt;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.util.wrapper.WrapperObject;
import mz.mzlib.util.wrapper.WrapConstructor;
import mz.mzlib.util.wrapper.WrapperCreator;

@WrapMinecraftClass({@VersionName(end = 1400, name = "net.minecraft.nbt.NbtInt"), @VersionName(begin = 1400, end = 1605, name = "net.minecraft.nbt.IntTag"), @VersionName(begin = 1605, name = "net.minecraft.nbt.NbtInt")})
public interface NBTInt extends NBTElement
{
    @SuppressWarnings("deprecation")
    @WrapperCreator
    static NBTInt create(Object wrapped)
    {
        return WrapperObject.create(NBTInt.class, wrapped);
    }

    @WrapConstructor
    NBTInt staticNewInstance(int value);
    static NBTInt newInstance(int value)
    {
        return create(null).staticNewInstance(value);
    }

    @WrapMinecraftFieldAccessor(@VersionName(name = "value"))
    int getValue();
    @WrapMinecraftFieldAccessor(@VersionName(name = "value"))
    void setValue(int value);
}

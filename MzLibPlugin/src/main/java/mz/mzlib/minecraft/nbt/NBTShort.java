package mz.mzlib.minecraft.nbt;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.util.wrapper.WrapperObject;
import mz.mzlib.util.wrapper.WrapConstructor;
import mz.mzlib.util.wrapper.WrapperCreator;

@WrapMinecraftClass({@VersionName(end = 1400, name = "net.minecraft.nbt.NbtShort"), @VersionName(begin = 1400, end = 1605, name = "net.minecraft.nbt.ShortTag"), @VersionName(begin = 1605, name = "net.minecraft.nbt.NbtShort")})
public interface NBTShort extends NBTElement
{
    @SuppressWarnings("deprecation")
    @WrapperCreator
    static NBTShort create(Object wrapped)
    {
        return WrapperObject.create(NBTShort.class, wrapped);
    }

    @WrapConstructor
    NBTShort staticNewInstance(short value);
    static NBTShort newInstance(short value)
    {
        return create(null).staticNewInstance(value);
    }

    @WrapMinecraftFieldAccessor(@VersionName(name = "value"))
    short getValue();
    @WrapMinecraftFieldAccessor(@VersionName(name = "value"))
    void setValue(short value);
}

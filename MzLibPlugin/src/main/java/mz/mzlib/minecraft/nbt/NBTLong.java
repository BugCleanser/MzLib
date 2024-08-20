package mz.mzlib.minecraft.nbt;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.util.wrapper.WrapperObject;
import mz.mzlib.util.wrapper.WrapConstructor;
import mz.mzlib.util.wrapper.WrapperCreator;

@WrapMinecraftClass({@VersionName(end = 1400, name = "net.minecraft.nbt.NbtLong"), @VersionName(begin = 1400, end = 1605, name = "net.minecraft.nbt.LongTag"), @VersionName(begin = 1605, name = "net.minecraft.nbt.NbtLong")})
public interface NBTLong extends NBTElement
{
    @SuppressWarnings("deprecation")
    @WrapperCreator
    static NBTLong create(Object wrapped)
    {
        return WrapperObject.create(NBTLong.class, wrapped);
    }

    @WrapConstructor
    NBTLong staticNewInstance(long value);
    static NBTLong newInstance(long value)
    {
        return create(null).staticNewInstance(value);
    }

    @WrapMinecraftFieldAccessor(@VersionName(name = "value"))
    long getValue();
    @WrapMinecraftFieldAccessor(@VersionName(name = "value"))
    void setValue(long value);
}

package mz.mzlib.minecraft.nbt;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.util.wrapper.WrapperObject;
import mz.mzlib.util.wrapper.WrapConstructor;
import mz.mzlib.util.wrapper.WrapperCreator;

@WrapMinecraftClass({@VersionName(end = 1400, name = "net.minecraft.nbt.NbtByteArray"), @VersionName(begin = 1400, end = 1605, name = "net.minecraft.nbt.ByteArrayTag"), @VersionName(begin = 1605, name = "net.minecraft.nbt.NbtByteArray")})
public interface NBTByteArray extends NBTElement
{
    @SuppressWarnings("deprecation")
    @WrapperCreator
    static NBTByteArray create(Object wrapped)
    {
        return WrapperObject.create(NBTByteArray.class, wrapped);
    }

    @WrapConstructor
    NBTByteArray staticNewInstance(byte[] value);
    static NBTByteArray newInstance(byte[] value)
    {
        return create(null).staticNewInstance(value);
    }

    @WrapMinecraftFieldAccessor(@VersionName(name = "value"))
    byte[] getValue();
    @WrapMinecraftFieldAccessor(@VersionName(name = "value"))
    void setValue(byte[] value);

    default byte get(int index)
    {
        return this.getValue()[index];
    }

    default void set(int index, byte value)
    {
        this.getValue()[index] = value;
    }
}

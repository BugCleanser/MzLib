package mz.mzlib.minecraft.nbt;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.util.wrapper.WrapperObject;
import mz.mzlib.util.wrapper.WrapConstructor;
import mz.mzlib.util.wrapper.WrapperCreator;

@WrapMinecraftClass({@VersionName(end = 1400, name = "net.minecraft.nbt.NbtIntArray"), @VersionName(begin = 1400, end = 1605, name = "net.minecraft.nbt.IntArrayTag"), @VersionName(begin = 1605, name = "net.minecraft.nbt.NbtIntArray")})
public interface NBTIntArray extends NBTElement
{
    @SuppressWarnings("deprecation")
    @WrapperCreator
    static NBTIntArray create(Object wrapped)
    {
        return WrapperObject.create(NBTIntArray.class, wrapped);
    }

    @WrapConstructor
    NBTIntArray staticNewInstance(int[] value);
    static NBTIntArray newInstance(int[] value)
    {
        return create(null).staticNewInstance(value);
    }

    @WrapMinecraftFieldAccessor(@VersionName(name = "value"))
    int[] getValue();
    @WrapMinecraftFieldAccessor(@VersionName(name = "value"))
    void setValue(int[] value);

    default int get(int index)
    {
        return this.getValue()[index];
    }

    default void set(int index, int value)
    {
        this.getValue()[index] = value;
    }
}

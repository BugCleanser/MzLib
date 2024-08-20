package mz.mzlib.minecraft.nbt;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.WrapperObject;
import mz.mzlib.util.wrapper.WrapConstructor;
import mz.mzlib.util.wrapper.WrapperCreator;

@WrapMinecraftClass({@VersionName(end = 1400, name = "net.minecraft.nbt.NbtCompound"), @VersionName(begin = 1400, end = 1605, name = "net.minecraft.nbt.CompoundTag"), @VersionName(begin = 1605, name = "net.minecraft.nbt.NbtCompound")})
public interface NBTCompound extends NBTElement
{
    @SuppressWarnings("deprecation")
    @WrapperCreator
    static NBTCompound create(Object wrapped)
    {
        return WrapperObject.create(NBTCompound.class, wrapped);
    }

    @WrapConstructor
    NBTCompound staticNewInstance();

    static NBTCompound newInstance()
    {
        return create(null).staticNewInstance();
    }

    @WrapMinecraftMethod(@VersionName(name = "get"))
    NBTElement get0(String key);
    default NBTElement get(String key)
    {
        return NBTElement.autoWrapper.cast(this.get0(key));
    }

    @WrapMinecraftMethod(@VersionName(name = "put"))
    void put(String key, NBTElement value);

    default NBTCompound getNBTCompound(String key)
    {
        return NBTCompound.create(this.get0(key).getWrapped());
    }
    default byte getByte(String key)
    {
        return NBTByte.create(this.get0(key).getWrapped()).getValue();
    }
    default int getInt(String key)
    {
        return NBTInt.create(this.get0(key).getWrapped()).getValue();
    }
    default long getLong(String key)
    {
        return NBTLong.create(this.get0(key).getWrapped()).getValue();
    }
    default float getFloat(String key)
    {
        return NBTFloat.create(this.get0(key).getWrapped()).getValue();
    }
    default double getDouble(String key)
    {
        return NBTDouble.create(this.get0(key).getWrapped()).getValue();
    }
    default String getString(String key)
    {
        return NBTString.create(this.get0(key).getWrapped()).getValue();
    }
    default NBTList getNBTList(String key)
    {
        return NBTList.create(this.get0(key).getWrapped());
    }
    default byte[] getByteArray(String key)
    {
        return NBTByteArray.create(this.get0(key).getWrapped()).getValue();
    }
    default int[] getIntArray(String key)
    {
        return NBTIntArray.create(this.get0(key).getWrapped()).getValue();
    }
    default long[] getLongArray(String key)
    {
        return NBTLongArray.create(this.get0(key).getWrapped()).getValue();
    }
}

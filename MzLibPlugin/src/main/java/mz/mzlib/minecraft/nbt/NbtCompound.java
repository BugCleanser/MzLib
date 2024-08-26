package mz.mzlib.minecraft.nbt;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.WrapperObject;
import mz.mzlib.util.wrapper.WrapConstructor;
import mz.mzlib.util.wrapper.WrapperCreator;
import net.minecraft.server.v1_12_R1.NBTTagCompound;

import java.util.function.Function;

@WrapMinecraftClass({@VersionName(end = 1400, name = "net.minecraft.nbt.NbtCompound"), @VersionName(begin = 1400, end = 1605, name = "net.minecraft.nbt.CompoundTag"), @VersionName(begin = 1605, name = "net.minecraft.nbt.NbtCompound")})
public interface NbtCompound extends NbtElement
{
    @SuppressWarnings("deprecation")
    @WrapperCreator
    static NbtCompound create(Object wrapped)
    {
        return WrapperObject.create(NbtCompound.class, wrapped);
    }

    @WrapConstructor
    NbtCompound staticNewInstance();

    static NbtCompound newInstance()
    {
        return create(null).staticNewInstance();
    }

    @WrapMinecraftMethod(@VersionName(name = "get"))
    NbtElement get(String key);
    default <T extends NbtElement> T get(String key, Function<Object, T> wrapperCreator)
    {
        return this.get(key).castTo(wrapperCreator);
    }

    @WrapMinecraftMethod(@VersionName(name = "put"))
    void put(String key, NbtElement value);

    default NbtCompound getNBTCompound(String key)
    {
        return this.get(key, NbtCompound::create);
    }
    default byte getByte(String key)
    {
        return this.get(key, NbtByte::create).getValue();
    }
    default int getInt(String key)
    {
        return this.get(key, NbtInt::create).getValue();
    }
    default long getLong(String key)
    {
        return this.get(key, NbtLong::create).getValue();
    }
    default float getFloat(String key)
    {
        return this.get(key, NbtFloat::create).getValue();
    }
    default double getDouble(String key)
    {
        return this.get(key, NbtDouble::create).getValue();
    }
    default String getString(String key)
    {
        return this.get(key, NbtString::create).getValue();
    }
    default NbtList getNBTList(String key)
    {
        return this.get(key, NbtList::create);
    }
    default NbtByteArray getByteArray(String key)
    {
        return this.get(key, NbtByteArray::create);
    }
    default NbtIntArray getIntArray(String key)
    {
        return this.get(key, NbtIntArray::create);
    }
    default NbtLongArray getLongArray(String key)
    {
        return this.get(key, NbtLongArray::create);
    }

    @WrapMinecraftMethod(@VersionName(name = "shallowCopy"))
    NBTTagCompound shallowCopy();
}

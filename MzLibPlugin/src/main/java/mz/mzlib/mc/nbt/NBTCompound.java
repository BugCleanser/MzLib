package mz.mzlib.mc.nbt;

import mz.mzlib.mc.VersionName;
import mz.mzlib.mc.delegator.DelegatorMinecraftClass;
import mz.mzlib.mc.delegator.DelegatorMinecraftMethod;
import mz.mzlib.util.delegator.Delegator;
import mz.mzlib.util.delegator.DelegatorConstructor;
import mz.mzlib.util.delegator.DelegatorCreator;

@DelegatorMinecraftClass({@VersionName(end = 1400, name = "net.minecraft.nbt.NbtCompound"), @VersionName(begin = 1400, end = 1605, name = "net.minecraft.nbt.CompoundTag"), @VersionName(begin = 1605, name = "net.minecraft.nbt.NbtCompound")})
public interface NBTCompound extends NBTElement
{
    @SuppressWarnings("deprecation")
    @DelegatorCreator
    static NBTCompound create(Object delegate)
    {
        return Delegator.create(NBTCompound.class, delegate);
    }

    @DelegatorConstructor
    NBTCompound staticNewInstance();

    static NBTCompound newInstance()
    {
        return create(null).staticNewInstance();
    }

    @DelegatorMinecraftMethod(@VersionName(name = "get"))
    NBTElement get0(String key);
    default NBTElement get(String key)
    {
        return NBTElement.autoDelegator.cast(this.get0(key));
    }

    @DelegatorMinecraftMethod(@VersionName(name = "put"))
    void put(String key, NBTElement value);

    default NBTCompound getNBTCompound(String key)
    {
        return NBTCompound.create(this.get0(key).getDelegate());
    }
    default byte getByte(String key)
    {
        return NBTByte.create(this.get0(key).getDelegate()).getValue();
    }
    default int getInt(String key)
    {
        return NBTInt.create(this.get0(key).getDelegate()).getValue();
    }
    default long getLong(String key)
    {
        return NBTLong.create(this.get0(key).getDelegate()).getValue();
    }
    default float getFloat(String key)
    {
        return NBTFloat.create(this.get0(key).getDelegate()).getValue();
    }
    default double getDouble(String key)
    {
        return NBTDouble.create(this.get0(key).getDelegate()).getValue();
    }
    default String getString(String key)
    {
        return NBTString.create(this.get0(key).getDelegate()).getValue();
    }
    default NBTList getNBTList(String key)
    {
        return NBTList.create(this.get0(key).getDelegate());
    }
    default byte[] getByteArray(String key)
    {
        return NBTByteArray.create(this.get0(key).getDelegate()).getValue();
    }
    default int[] getIntArray(String key)
    {
        return NBTIntArray.create(this.get0(key).getDelegate()).getValue();
    }
    default long[] getLongArray(String key)
    {
        return NBTLongArray.create(this.get0(key).getDelegate()).getValue();
    }
}

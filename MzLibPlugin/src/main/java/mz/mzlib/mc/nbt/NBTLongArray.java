package mz.mzlib.mc.nbt;

import mz.mzlib.mc.VersionName;
import mz.mzlib.mc.delegator.DelegatorMinecraftClass;
import mz.mzlib.mc.delegator.DelegatorMinecraftFieldAccessor;
import mz.mzlib.util.delegator.Delegator;
import mz.mzlib.util.delegator.DelegatorConstructor;
import mz.mzlib.util.delegator.DelegatorCreator;

@DelegatorMinecraftClass({@VersionName(end = 1400, name = "net.minecraft.nbt.NbtLongArray"), @VersionName(begin = 1400, end = 1605, name = "net.minecraft.nbt.LongArrayTag"), @VersionName(begin = 1605, name = "net.minecraft.nbt.NbtLongArray")})
public interface NBTLongArray extends NBTElement
{
    @SuppressWarnings("deprecation")
    @DelegatorCreator
    static NBTLongArray create(Object delegate)
    {
        return Delegator.create(NBTLongArray.class, delegate);
    }

    @DelegatorConstructor
    NBTLongArray staticNewInstance(long[] value);
    static NBTLongArray newInstance(long[] value)
    {
        return create(null).staticNewInstance(value);
    }

    @DelegatorMinecraftFieldAccessor(@VersionName(name = "value"))
    long[] getValue();
    @DelegatorMinecraftFieldAccessor(@VersionName(name = "value"))
    void setValue(long[] value);

    default long get(int index)
    {
        return this.getValue()[index];
    }

    default void set(int index, long value)
    {
        this.getValue()[index] = value;
    }
}

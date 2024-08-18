package mz.mzlib.mc.nbt;

import mz.mzlib.mc.VersionName;
import mz.mzlib.mc.delegator.DelegatorMinecraftClass;
import mz.mzlib.mc.delegator.DelegatorMinecraftFieldAccessor;
import mz.mzlib.util.delegator.Delegator;
import mz.mzlib.util.delegator.DelegatorConstructor;
import mz.mzlib.util.delegator.DelegatorCreator;

@DelegatorMinecraftClass({@VersionName(end = 1400, name = "net.minecraft.nbt.NbtByteArray"), @VersionName(begin = 1400, end = 1605, name = "net.minecraft.nbt.ByteArrayTag"), @VersionName(begin = 1605, name = "net.minecraft.nbt.NbtByteArray")})
public interface NBTByteArray extends NBTElement
{
    @SuppressWarnings("deprecation")
    @DelegatorCreator
    static NBTByteArray create(Object delegate)
    {
        return Delegator.create(NBTByteArray.class, delegate);
    }

    @DelegatorConstructor
    NBTByteArray staticNewInstance(byte[] value);
    static NBTByteArray newInstance(byte[] value)
    {
        return create(null).staticNewInstance(value);
    }

    @DelegatorMinecraftFieldAccessor(@VersionName(name = "value"))
    byte[] getValue();
    @DelegatorMinecraftFieldAccessor(@VersionName(name = "value"))
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

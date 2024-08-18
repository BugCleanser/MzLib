package mz.mzlib.mc.nbt;

import mz.mzlib.mc.VersionName;
import mz.mzlib.mc.delegator.DelegatorMinecraftClass;
import mz.mzlib.mc.delegator.DelegatorMinecraftFieldAccessor;
import mz.mzlib.util.delegator.Delegator;
import mz.mzlib.util.delegator.DelegatorConstructor;
import mz.mzlib.util.delegator.DelegatorCreator;

@DelegatorMinecraftClass({@VersionName(end = 1400, name = "net.minecraft.nbt.NbtIntArray"), @VersionName(begin = 1400, end = 1605, name = "net.minecraft.nbt.IntArrayTag"), @VersionName(begin = 1605, name = "net.minecraft.nbt.NbtIntArray")})
public interface NBTIntArray extends NBTElement
{
    @SuppressWarnings("deprecation")
    @DelegatorCreator
    static NBTIntArray create(Object delegate)
    {
        return Delegator.create(NBTIntArray.class, delegate);
    }

    @DelegatorConstructor
    NBTIntArray staticNewInstance(int[] value);
    static NBTIntArray newInstance(int[] value)
    {
        return create(null).staticNewInstance(value);
    }

    @DelegatorMinecraftFieldAccessor(@VersionName(name = "value"))
    int[] getValue();
    @DelegatorMinecraftFieldAccessor(@VersionName(name = "value"))
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

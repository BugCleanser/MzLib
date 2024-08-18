package mz.mzlib.mc.nbt;

import mz.mzlib.mc.VersionName;
import mz.mzlib.mc.delegator.DelegatorMinecraftClass;
import mz.mzlib.mc.delegator.DelegatorMinecraftFieldAccessor;
import mz.mzlib.util.delegator.Delegator;
import mz.mzlib.util.delegator.DelegatorConstructor;
import mz.mzlib.util.delegator.DelegatorCreator;

@DelegatorMinecraftClass({@VersionName(end = 1400, name = "net.minecraft.nbt.NbtByte"), @VersionName(begin = 1400, end = 1605, name = "net.minecraft.nbt.ByteTag"), @VersionName(begin = 1605, name = "net.minecraft.nbt.NbtByte")})
public interface NBTByte extends NBTElement
{
    @SuppressWarnings("deprecation")
    @DelegatorCreator
    static NBTByte create(Object delegate)
    {
        return Delegator.create(NBTByte.class, delegate);
    }

    @DelegatorConstructor
    NBTByte staticNewInstance(byte value);

    static NBTByte newInstance(byte value)
    {
        return create(null).staticNewInstance(value);
    }

    @DelegatorMinecraftFieldAccessor(@VersionName(name = "value"))
    byte getValue();

    @DelegatorMinecraftFieldAccessor(@VersionName(name = "value"))
    void setValue(byte value);
}

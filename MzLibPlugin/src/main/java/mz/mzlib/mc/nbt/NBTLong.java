package mz.mzlib.mc.nbt;

import mz.mzlib.mc.VersionName;
import mz.mzlib.mc.delegator.DelegatorMinecraftClass;
import mz.mzlib.mc.delegator.DelegatorMinecraftFieldAccessor;
import mz.mzlib.util.delegator.Delegator;
import mz.mzlib.util.delegator.DelegatorConstructor;
import mz.mzlib.util.delegator.DelegatorCreator;

@DelegatorMinecraftClass({@VersionName(end = 1400, name = "net.minecraft.nbt.NbtLong"), @VersionName(begin = 1400, end = 1605, name = "net.minecraft.nbt.LongTag"), @VersionName(begin = 1605, name = "net.minecraft.nbt.NbtLong")})
public interface NBTLong extends NBTElement
{
    @SuppressWarnings("deprecation")
    @DelegatorCreator
    static NBTLong create(Object delegate)
    {
        return Delegator.create(NBTLong.class, delegate);
    }

    @DelegatorConstructor
    NBTLong staticNewInstance(long value);
    static NBTLong newInstance(long value)
    {
        return create(null).staticNewInstance(value);
    }

    @DelegatorMinecraftFieldAccessor(@VersionName(name = "value"))
    long getValue();
    @DelegatorMinecraftFieldAccessor(@VersionName(name = "value"))
    void setValue(long value);
}

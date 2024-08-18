package mz.mzlib.mc.nbt;

import mz.mzlib.mc.VersionName;
import mz.mzlib.mc.delegator.DelegatorMinecraftClass;
import mz.mzlib.mc.delegator.DelegatorMinecraftFieldAccessor;
import mz.mzlib.util.delegator.Delegator;
import mz.mzlib.util.delegator.DelegatorConstructor;
import mz.mzlib.util.delegator.DelegatorCreator;

@DelegatorMinecraftClass({@VersionName(end = 1400, name = "net.minecraft.nbt.NbtInt"), @VersionName(begin = 1400, end = 1605, name = "net.minecraft.nbt.IntTag"), @VersionName(begin = 1605, name = "net.minecraft.nbt.NbtInt")})
public interface NBTInt extends NBTElement
{
    @SuppressWarnings("deprecation")
    @DelegatorCreator
    static NBTInt create(Object delegate)
    {
        return Delegator.create(NBTInt.class, delegate);
    }

    @DelegatorConstructor
    NBTInt staticNewInstance(int value);
    static NBTInt newInstance(int value)
    {
        return create(null).staticNewInstance(value);
    }

    @DelegatorMinecraftFieldAccessor(@VersionName(name = "value"))
    int getValue();
    @DelegatorMinecraftFieldAccessor(@VersionName(name = "value"))
    void setValue(int value);
}

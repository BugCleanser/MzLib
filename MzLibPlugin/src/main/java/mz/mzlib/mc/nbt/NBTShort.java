package mz.mzlib.mc.nbt;

import mz.mzlib.mc.VersionName;
import mz.mzlib.mc.delegator.DelegatorMinecraftClass;
import mz.mzlib.mc.delegator.DelegatorMinecraftFieldAccessor;
import mz.mzlib.util.delegator.Delegator;
import mz.mzlib.util.delegator.DelegatorConstructor;
import mz.mzlib.util.delegator.DelegatorCreator;

@DelegatorMinecraftClass({@VersionName(end = 1400, name = "net.minecraft.nbt.NbtShort"), @VersionName(begin = 1400, end = 1605, name = "net.minecraft.nbt.ShortTag"), @VersionName(begin = 1605, name = "net.minecraft.nbt.NbtShort")})
public interface NBTShort extends NBTElement
{
    @SuppressWarnings("deprecation")
    @DelegatorCreator
    static NBTShort create(Object delegate)
    {
        return Delegator.create(NBTShort.class, delegate);
    }

    @DelegatorConstructor
    NBTShort staticNewInstance(short value);
    static NBTShort newInstance(short value)
    {
        return create(null).staticNewInstance(value);
    }

    @DelegatorMinecraftFieldAccessor(@VersionName(name = "value"))
    short getValue();
    @DelegatorMinecraftFieldAccessor(@VersionName(name = "value"))
    void setValue(short value);
}

package mz.mzlib.mc.nbt;

import mz.mzlib.mc.VersionName;
import mz.mzlib.mc.delegator.DelegatorMinecraftClass;
import mz.mzlib.mc.delegator.DelegatorMinecraftFieldAccessor;
import mz.mzlib.util.delegator.Delegator;
import mz.mzlib.util.delegator.DelegatorConstructor;
import mz.mzlib.util.delegator.DelegatorCreator;

@DelegatorMinecraftClass({@VersionName(end = 1400, name = "net.minecraft.nbt.NbtDouble"), @VersionName(begin = 1400, end = 1605, name = "net.minecraft.nbt.DoubleTag"), @VersionName(begin = 1605, name = "net.minecraft.nbt.NbtDouble")})
public interface NBTDouble extends NBTElement
{
    @SuppressWarnings("deprecation")
    @DelegatorCreator
    static NBTDouble create(Object delegate)
    {
        return Delegator.create(NBTDouble.class, delegate);
    }

    @DelegatorConstructor
    NBTDouble staticNewInstance(double value);

    static NBTDouble newInstance(double value)
    {
        return create(null).staticNewInstance(value);
    }

    @DelegatorMinecraftFieldAccessor(@VersionName(name = "value"))
    double getValue();

    @DelegatorMinecraftFieldAccessor(@VersionName(name = "value"))
    void setValue(double value);
}

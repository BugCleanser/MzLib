package mz.mzlib.mc.nbt;

import mz.mzlib.mc.VersionName;
import mz.mzlib.mc.delegator.DelegatorMinecraftClass;
import mz.mzlib.mc.delegator.DelegatorMinecraftFieldAccessor;
import mz.mzlib.util.delegator.Delegator;
import mz.mzlib.util.delegator.DelegatorConstructor;
import mz.mzlib.util.delegator.DelegatorCreator;

@DelegatorMinecraftClass({@VersionName(end = 1400, name = "net.minecraft.nbt.NbtFloat"), @VersionName(begin = 1400, end = 1605, name = "net.minecraft.nbt.FloatTag"), @VersionName(begin = 1605, name = "net.minecraft.nbt.NbtFloat")})
public interface NBTFloat extends NBTElement
{
    @SuppressWarnings("deprecation")
    @DelegatorCreator
    static NBTFloat create(Object delegate)
    {
        return Delegator.create(NBTFloat.class, delegate);
    }

    @DelegatorConstructor
    NBTFloat staticNewInstance(float value);

    static NBTFloat newInstance(float value)
    {
        return create(null).staticNewInstance(value);
    }

    @DelegatorMinecraftFieldAccessor(@VersionName(name = "value"))
    float getValue();

    @DelegatorMinecraftFieldAccessor(@VersionName(name = "value"))
    void setValue(float value);
}

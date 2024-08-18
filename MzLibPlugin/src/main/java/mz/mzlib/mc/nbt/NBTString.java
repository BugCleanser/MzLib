package mz.mzlib.mc.nbt;

import mz.mzlib.mc.VersionName;
import mz.mzlib.mc.delegator.DelegatorMinecraftClass;
import mz.mzlib.mc.delegator.DelegatorMinecraftFieldAccessor;
import mz.mzlib.util.delegator.Delegator;
import mz.mzlib.util.delegator.DelegatorConstructor;
import mz.mzlib.util.delegator.DelegatorCreator;

@DelegatorMinecraftClass({@VersionName(end = 1400, name = "net.minecraft.nbt.NbtString"), @VersionName(begin = 1400, end = 1605, name = "net.minecraft.nbt.StringTag"), @VersionName(begin = 1605, name = "net.minecraft.nbt.NbtString")})
public interface NBTString extends NBTElement
{
    @SuppressWarnings("deprecation")
    @DelegatorCreator
    static NBTString create(Object delegate)
    {
        return Delegator.create(NBTString.class, delegate);
    }

    @DelegatorConstructor
    NBTString staticNewInstance(String value);
    static NBTString newInstance(String value)
    {
        return create(null).staticNewInstance(value);
    }

    @DelegatorMinecraftFieldAccessor(@VersionName(name = "value"))
    String getValue();
    @DelegatorMinecraftFieldAccessor(@VersionName(name = "value"))
    void setValue(String value);
}

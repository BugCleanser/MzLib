package mz.mzlib.mc.nbt;

import mz.mzlib.mc.VersionName;
import mz.mzlib.mc.delegator.DelegatorMinecraftClass;
import mz.mzlib.util.delegator.AutoDelegator;
import mz.mzlib.util.delegator.Delegator;
import mz.mzlib.util.delegator.DelegatorCreator;

@DelegatorMinecraftClass({@VersionName(name = "net.minecraft.nbt.NbtElement", end = 1400), @VersionName(name = "net.minecraft.nbt.Tag", begin = 1400, end = 1605), @VersionName(name = "net.minecraft.nbt.NbtElement", begin = 1605)})
public interface NBTElement extends Delegator
{
    @SuppressWarnings("deprecation")
    @DelegatorCreator
    static NBTElement create(Object delegate)
    {
        return Delegator.create(NBTElement.class, delegate);
    }

    AutoDelegator<NBTElement> autoDelegator = new AutoDelegator<>(NBTElement.class, NBTCompound.class); // TODO
}

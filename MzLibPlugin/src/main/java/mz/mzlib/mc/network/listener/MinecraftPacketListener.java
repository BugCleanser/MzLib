package mz.mzlib.mc.network.listener;

import mz.mzlib.mc.VersionName;
import mz.mzlib.mc.delegator.DelegatorMinecraftClass;
import mz.mzlib.util.delegator.Delegator;
import mz.mzlib.util.delegator.DelegatorCreator;

@DelegatorMinecraftClass(@VersionName(name="net.minecraft.network.listener.PacketListener"))
public interface MinecraftPacketListener extends Delegator
{
    @SuppressWarnings("deprecation")
    @DelegatorCreator
    static MinecraftPacketListener create(Object object)
    {
        return Delegator.create(MinecraftPacketListener.class, object);
    }
}

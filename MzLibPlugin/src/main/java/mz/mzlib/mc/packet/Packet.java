package mz.mzlib.mc.packet;

import mz.mzlib.mc.VersionName;
import mz.mzlib.mc.delegator.DelegatorMinecraftClass;
import mz.mzlib.util.delegator.Delegator;
import mz.mzlib.util.delegator.DelegatorCreator;

@DelegatorMinecraftClass({@VersionName(name = "net.minecraft.network.Packet", end = 1904), @VersionName(name = "net.minecraft.network.packet.Packet", begin = 1904)})
public interface Packet extends Delegator
{
    @SuppressWarnings("deprecation")
    @DelegatorCreator
    static Packet create(Object delegate)
    {
        return Delegator.create(Packet.class, delegate);
    }
}

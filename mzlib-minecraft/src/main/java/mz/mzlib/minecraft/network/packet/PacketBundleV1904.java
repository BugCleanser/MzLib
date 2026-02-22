package mz.mzlib.minecraft.network.packet;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.util.proxy.IteratorProxy;
import mz.mzlib.util.wrapper.WrapperFactory;

@VersionRange(begin = 1904)
@WrapMinecraftClass(@VersionName(name = "net.minecraft.network.packet.BundlePacket"))
public interface PacketBundleV1904 extends Packet
{
    WrapperFactory<PacketBundleV1904> FACTORY = WrapperFactory.of(PacketBundleV1904.class);
    @WrapMinecraftFieldAccessor(@VersionName(name = "packets"))
    Iterable<?> getPackets0();

    default Iterable<Packet> getPackets()
    {
        return IteratorProxy.iterable(this.getPackets0(), Packet.FACTORY::create);
    }
}


package mz.mzlib.minecraft.network.packet;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.util.wrapper.IteratorWrapper;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@VersionRange(begin=1904)
@WrapMinecraftClass(@VersionName(name="net.minecraft.network.packet.BundlePacket"))
public interface PacketBundleV1904 extends Packet
{
    @WrapperCreator
    static PacketBundleV1904 create(Object wrapped)
    {
        return WrapperObject.create(PacketBundleV1904.class, wrapped);
    }
    
    @WrapMinecraftFieldAccessor(@VersionName(name="packets"))
    Iterable<?> getPackets0();
    
    default Iterable<Packet> getPackets()
    {
        return IteratorWrapper.iterable(this.getPackets0(), Packet::create);
    }
}

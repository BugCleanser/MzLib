package mz.mzlib.minecraft.network.packet;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.util.wrapper.IteratorWrapper;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

// TODO: versioning
@WrapMinecraftClass(@VersionName(name="net.minecraft.network.packet.BundlePacket"))
public interface PacketBundle extends Packet
{
    @WrapperCreator
    static PacketBundle create(Object wrapped)
    {
        return WrapperObject.create(PacketBundle.class, wrapped);
    }
    
    @WrapMinecraftFieldAccessor(@VersionName(name="packets"))
    Iterable<?> getPackets0();
    default Iterable<Packet> getPackets()
    {
        return IteratorWrapper.iterable(this.getPackets0(), Packet::create);
    }
}

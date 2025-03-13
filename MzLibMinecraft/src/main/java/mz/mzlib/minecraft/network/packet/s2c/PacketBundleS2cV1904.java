package mz.mzlib.minecraft.network.packet.s2c;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.network.packet.Packet;
import mz.mzlib.minecraft.network.packet.PacketBundleV1904;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.util.proxy.IteratorProxy;
import mz.mzlib.util.wrapper.WrapConstructor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@VersionRange(begin=1904)
@WrapMinecraftClass(@VersionName(name="net.minecraft.network.packet.s2c.play.BundleS2CPacket"))
public interface PacketBundleS2cV1904 extends WrapperObject, PacketBundleV1904
{
    WrapperFactory<PacketBundleS2cV1904> FACTORY = WrapperFactory.find(PacketBundleS2cV1904.class);
    @Deprecated
    @WrapperCreator
    static PacketBundleS2cV1904 create(Object wrapped)
    {
        return WrapperObject.create(PacketBundleS2cV1904.class, wrapped);
    }
    
    static PacketBundleS2cV1904 newInstance(Iterable<Packet> packets)
    {
        return newInstance0(IteratorProxy.iterable(packets, Packet::getWrapped));
    }
    static PacketBundleS2cV1904 newInstance0(Iterable<?> packets0)
    {
        return create(null).staticNewInstance0(packets0);
    }
    @WrapConstructor
    PacketBundleS2cV1904 staticNewInstance0(Iterable<?> packets0);
}

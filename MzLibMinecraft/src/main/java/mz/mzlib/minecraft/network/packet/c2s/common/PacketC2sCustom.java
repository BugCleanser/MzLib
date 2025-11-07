package mz.mzlib.minecraft.network.packet.c2s.common;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.network.packet.ByteBufPacket;
import mz.mzlib.minecraft.network.packet.Packet;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.util.wrapper.SpecificImpl;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass({
    @VersionName(name = "net.minecraft.network.packet.c2s.play.CustomPayloadC2SPacket", end = 1400),
    @VersionName(name = "net.minecraft.class_2817", begin = 1400)
})
public interface PacketC2sCustom extends WrapperObject, Packet
{
    WrapperFactory<PacketC2sCustom> FACTORY = WrapperFactory.of(PacketC2sCustom.class);
    @Deprecated
    @WrapperCreator
    static PacketC2sCustom create(Object wrapped)
    {
        return WrapperObject.create(PacketC2sCustom.class, wrapped);
    }

    @VersionRange(end = 1300)
    @WrapMinecraftFieldAccessor(@VersionName(name = "channel"))
    String getChannelV_1300();

    ByteBufPacket getPayload();

    @SpecificImpl("getPayload")
    @VersionRange(end = 1400)
    @WrapMinecraftFieldAccessor(@VersionName(name = "payload"))
    ByteBufPacket getPayloadV_1400();

    @SpecificImpl("getPayload")
    @VersionRange(begin = 1400)
    default ByteBufPacket getPayloadV1400()
    {
        throw new UnsupportedOperationException();
    }
}

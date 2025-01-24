package mz.mzlib.minecraft.incomprehensible.network;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.network.packet.PacketDirection;
import mz.mzlib.minecraft.network.packet.ByteBufPacket;
import mz.mzlib.minecraft.network.packet.Packet;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

// TODO: versioning
@VersionRange(begin=1400, end=2005)
@WrapMinecraftClass(@VersionName(name="net.minecraft.network.NetworkState"))
public interface NetworkPhasePacketManagerV1400_2005 extends WrapperObject
{
    @WrapperCreator
    static NetworkPhasePacketManagerV1400_2005 create(Object wrapped)
    {
        return WrapperObject.create(NetworkPhasePacketManagerV1400_2005.class, wrapped);
    }
    
    static NetworkPhasePacketManagerV1400_2005 handshake()
    {
        return create(null).staticHandshake();
    }
    @WrapMinecraftFieldAccessor({@VersionName(name="field_11689", begin=1400, end=1500), @VersionName(name="field_20590", begin=1500)})
    NetworkPhasePacketManagerV1400_2005 staticHandshake();
    
    static NetworkPhasePacketManagerV1400_2005 play()
    {
        return create(null).staticPlay();
    }
    @WrapMinecraftFieldAccessor({@VersionName(name="field_11690", begin=1400, end=1500), @VersionName(name="field_20591", begin=1500)})
    NetworkPhasePacketManagerV1400_2005 staticPlay();
    
    static NetworkPhasePacketManagerV1400_2005 query()
    {
        return create(null).staticQuery();
    }
    @WrapMinecraftFieldAccessor({@VersionName(name="field_11691", begin=1400, end=1500), @VersionName(name="field_20592", begin=1500)})
    NetworkPhasePacketManagerV1400_2005 staticQuery();
    
    static NetworkPhasePacketManagerV1400_2005 login()
    {
        return create(null).staticLogin();
    }
    @WrapMinecraftFieldAccessor({@VersionName(name="field_11688", begin=1400, end=1500), @VersionName(name="field_20593", begin=1500)})
    NetworkPhasePacketManagerV1400_2005 staticLogin();
    
    @WrapMinecraftMethod(@VersionName(name="getPacketId"))
    Integer getPacketId(PacketDirection direction, Packet packet);
    
    @VersionRange(end=1700)
    @WrapMinecraftMethod(@VersionName(name="getPacketHandler"))
    Packet newPacketV_1700(PacketDirection direction, int packetId);
    
    @VersionRange(begin=1700)
    @WrapMinecraftMethod(@VersionName(name="getPacketHandler"))
    Packet decodePacketV1700(PacketDirection direction, int packetId, ByteBufPacket byteBuf);
}

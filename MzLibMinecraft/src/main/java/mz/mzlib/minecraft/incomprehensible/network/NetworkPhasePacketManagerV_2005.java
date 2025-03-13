package mz.mzlib.minecraft.incomprehensible.network;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.network.packet.ByteBufPacket;
import mz.mzlib.minecraft.network.packet.Packet;
import mz.mzlib.minecraft.network.packet.PacketDirection;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.minecraft.wrapper.WrapMinecraftInnerClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.SpecificImpl;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@VersionRange(end=2005)
@WrapMinecraftClass(@VersionName(name="net.minecraft.network.NetworkState"))
public interface NetworkPhasePacketManagerV_2005 extends WrapperObject
{
    WrapperFactory<NetworkPhasePacketManagerV_2005> FACTORY = WrapperFactory.find(NetworkPhasePacketManagerV_2005.class);
    @Deprecated
    @WrapperCreator
    static NetworkPhasePacketManagerV_2005 create(Object wrapped)
    {
        return WrapperObject.create(NetworkPhasePacketManagerV_2005.class, wrapped);
    }
    
    static NetworkPhasePacketManagerV_2005 handshake()
    {
        return create(null).staticHandshake();
    }
    
    @WrapMinecraftFieldAccessor({@VersionName(name="HANDSHAKING", end=1400), @VersionName(name="field_11689", begin=1400, end=1500), @VersionName(name="field_20590", begin=1500)})
    NetworkPhasePacketManagerV_2005 staticHandshake();
    
    static NetworkPhasePacketManagerV_2005 play()
    {
        return create(null).staticPlay();
    }
    
    @WrapMinecraftFieldAccessor({@VersionName(name="PLAY", end=1400), @VersionName(name="field_11690", begin=1400, end=1500), @VersionName(name="field_20591", begin=1500)})
    NetworkPhasePacketManagerV_2005 staticPlay();
    
    static NetworkPhasePacketManagerV_2005 query()
    {
        return create(null).staticQuery();
    }
    
    @WrapMinecraftFieldAccessor({@VersionName(name="STATUS", end=1400), @VersionName(name="field_11691", begin=1400, end=1500), @VersionName(name="field_20592", begin=1500)})
    NetworkPhasePacketManagerV_2005 staticQuery();
    
    static NetworkPhasePacketManagerV_2005 login()
    {
        return create(null).staticLogin();
    }
    
    @WrapMinecraftFieldAccessor({@VersionName(name="LOGIN", end=1400), @VersionName(name="field_11688", begin=1400, end=1500), @VersionName(name="field_20593", begin=1500)})
    NetworkPhasePacketManagerV_2005 staticLogin();
    
    Integer getPacketId(PacketDirection direction, Packet packet);
    
    @SpecificImpl("getPacketId")
    @VersionRange(end=2002)
    @WrapMinecraftMethod({@VersionName(name="getRawId", end=1400), @VersionName(name="getPacketId", begin=1400)})
    Integer getPacketIdV_2002(PacketDirection direction, Packet packet);
    
    @SpecificImpl("getPacketId")
    @VersionRange(begin=2002)
    default Integer getPacketIdV2002(PacketDirection direction, Packet packet)
    {
        int result = this.getHandlerV2002(direction).getId0V2002(packet);
        return result==-1 ? null : result;
    }
    
    @VersionRange(end=1700)
    @WrapMinecraftMethod({@VersionName(name="createPacket", end=1400), @VersionName(name="method_10783", begin=1400)})
    Packet createPacketV_1700(PacketDirection direction, int packetId);
    
    @VersionRange(begin=1700)
    Packet decodePacketV1700(PacketDirection direction, int packetId, ByteBufPacket byteBuf);
    
    @SpecificImpl("decodePacketV1700")
    @VersionRange(begin=1700, end=2002)
    @WrapMinecraftMethod(@VersionName(name="method_10783"))
    Packet decodePacketV1700_2002(PacketDirection direction, int packetId, ByteBufPacket byteBuf);
    
    @VersionRange(begin=2002)
    @WrapMinecraftMethod(@VersionName(name="getHandler"))
    PacketHandlerV1500 getHandlerV2002(PacketDirection direction);
    
    @SpecificImpl("decodePacketV1700")
    @VersionRange(begin=2002)
    default Packet decodePacketV2002(PacketDirection direction, int packetId, ByteBufPacket byteBuf)
    {
        return this.getHandlerV2002(direction).decodePacketV1700(packetId, byteBuf);
    }
    
    @VersionRange(begin=1500)
    @WrapMinecraftInnerClass(outer=NetworkPhasePacketManagerV_2005.class, name=@VersionName(name="PacketHandler"))
    interface PacketHandlerV1500 extends WrapperObject
    {
        WrapperFactory<PacketHandlerV1500> FACTORY = WrapperFactory.find(PacketHandlerV1500.class);
        @Deprecated
        @WrapperCreator
        static PacketHandlerV1500 create(Object wrapped)
        {
            return WrapperObject.create(PacketHandlerV1500.class, wrapped);
        }
        
        @VersionRange(begin=1904, end=2002)
        @WrapMinecraftMethod(@VersionName(name="getId"))
        int getId0V1904_2002(Class<?> packetClass0);
        
        @VersionRange(begin=2002)
        @WrapMinecraftMethod(@VersionName(name="getId"))
        int getId0V2002(Packet packet);
        
        @VersionRange(begin=1700)
        @WrapMinecraftMethod(@VersionName(name="createPacket"))
        Packet decodePacketV1700(int packetId, ByteBufPacket byteBuf);
    }
}

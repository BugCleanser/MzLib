package mz.mzlib.minecraft.network.packet;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.UnpooledByteBufAllocator;
import mz.mzlib.minecraft.MinecraftPlatform;
import mz.mzlib.minecraft.MinecraftServer;
import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.incomprehensible.network.*;
import mz.mzlib.minecraft.incomprehensible.registry.ByteBufWithRegistriesV2005;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.SpecificImpl;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@WrapMinecraftClass({@VersionName(name="net.minecraft.network.Packet", end=1904), @VersionName(name="net.minecraft.network.packet.Packet", begin=1904)})
public interface Packet extends WrapperObject
{
    @WrapperCreator
    static Packet create(Object wrapped)
    {
        return WrapperObject.create(Packet.class, wrapped);
    }
    
    boolean isBundle();
    
    @VersionRange(end=1904)
    @SpecificImpl("isBundle")
    default boolean isBundleV_1904()
    {
        return false;
    }
    
    @VersionRange(begin=1904)
    @SpecificImpl("isBundle")
    default boolean isBundleV1904()
    {
        return this.isInstanceOf(PacketBundleV1904::create);
    }
    
    @VersionRange(end=1700)
    @WrapMinecraftMethod(@VersionName(name="read"))
    void readV_1700(ByteBufPacket byteBuf);
    
    @VersionRange(end=2005)
    @WrapMinecraftMethod(@VersionName(name="write"))
    void writeV_2005(ByteBufPacket byteBuf);
    
    <T extends Packet> T copy(ByteBufAllocator byteBufAllocator);
    
    @VersionRange(end=1400)
    @SpecificImpl("copy")
    default <T extends Packet> T copyV_1400(ByteBufAllocator byteBufAllocator)
    {
        // TODO
        throw new UnsupportedOperationException();
    }
    
    List<NetworkPhasePacketManagerV1400_2005> networkPhasePacketManagersV1400_2005 = MinecraftPlatform.instance.getVersion()>=1400 && MinecraftPlatform.instance.getVersion()<2005 ? Arrays.asList //
            ( //
                    NetworkPhasePacketManagerV1400_2005.play(), //
                    NetworkPhasePacketManagerV1400_2005.handshake(), //
                    NetworkPhasePacketManagerV1400_2005.login(), //
                    NetworkPhasePacketManagerV1400_2005.query() //
            ) : null;
    
    @VersionRange(begin=1400, end=1700)
    @SpecificImpl("copy")
    default <T extends Packet> T copyV1400_1700(ByteBufAllocator byteBufAllocator)
    {
        ByteBuf byteBuf = byteBufAllocator.buffer(4096);
        try
        {
            ByteBufPacket byteBufPacket = ByteBufPacket.newInstance(byteBuf);
            for(NetworkPhasePacketManagerV1400_2005 i: networkPhasePacketManagersV1400_2005)
            {
                PacketDirection direction = PacketDirection.s2c();
                Integer id = null;
                try
                {
                    id = i.getPacketId(direction, this);
                }
                catch(NullPointerException ignored)
                {
                }
                if(id==null)
                {
                    try
                    {
                        id = i.getPacketId(direction = PacketDirection.c2s(), this);
                    }
                    catch(NullPointerException ignored)
                    {
                    }
                }
                if(id==null)
                    continue;
                this.writeV_2005(byteBufPacket);
                T result = i.newPacketV_1700(direction, id).castTo(this::staticCreate);
                result.readV_1700(byteBufPacket);
                return result;
            }
            throw new UnsupportedOperationException();
        }
        finally
        {
            byteBuf.release();
        }
    }
    
    @VersionRange(begin=1700, end=2005)
    @SpecificImpl("copy")
    default <T extends Packet> T copyV1700_2005(ByteBufAllocator byteBufAllocator)
    {
        ByteBuf byteBuf = byteBufAllocator.buffer(4096);
        try
        {
            ByteBufPacket byteBufPacket = ByteBufPacket.newInstance(byteBuf);
            for(NetworkPhasePacketManagerV1400_2005 i: networkPhasePacketManagersV1400_2005)
            {
                PacketDirection direction = PacketDirection.s2c();
                Integer id = null;
                try
                {
                    id = i.getPacketId(direction, this);
                }
                catch(NullPointerException ignored)
                {
                }
                if(id==null)
                {
                    try
                    {
                        id = i.getPacketId(direction = PacketDirection.c2s(), this);
                    }
                    catch(NullPointerException ignored)
                    {
                    }
                }
                if(id==null)
                    continue;
                this.writeV_2005(byteBufPacket);
                return i.decodePacketV1700(direction, id, byteBufPacket).castTo(this::staticCreate);
            }
            throw new UnsupportedOperationException();
        }
        finally
        {
            byteBuf.release();
        }
    }
    
    List<NetworkPhaseSidedPacketManagerV2005> networkPhaseSidedPacketManagersV2005 = MinecraftPlatform.instance.getVersion()<2005 ? null : Arrays.asList //
            ( //
                    NetworkPlaySidedPacketManagerFactoriesV2005.s2c().make(ByteBufWithRegistriesV2005.method_56350(MinecraftServer.instance.getRegistriesV1602())), //
                    NetworkPlaySidedPacketManagerFactoriesV2005.c2s().make(ByteBufWithRegistriesV2005.method_56350(MinecraftServer.instance.getRegistriesV1602())), //
                    NetworkHandshakeSidedPacketManagersV2005.c2s(), //
                    NetworkConfigurationSidedPacketManagersV2005.s2c(), //
                    NetworkConfigurationSidedPacketManagersV2005.c2s(), //
                    NetworkQuerySidedPacketManagersV2005.s2c(), //
                    NetworkQuerySidedPacketManagersV2005.c2s(), //
                    NetworkLoginSidedPacketManagersV2005.s2c(), //
                    NetworkLoginSidedPacketManagersV2005.c2s() //
            );
    
    @VersionRange(begin=2005)
    @SpecificImpl("copy")
    default <T extends Packet> T copyV2005(ByteBufAllocator byteBufAllocator) // TODO optimize
    {
        ByteBuf byteBuf = byteBufAllocator.buffer(4096);
        try
        {
            RuntimeException exception = null;
            for(NetworkPhaseSidedPacketManagerV2005 i: networkPhaseSidedPacketManagersV2005)
            {
                try
                {
                    i.getCodec().encode(byteBuf, this.getWrapped());
                    return this.staticCreate(i.getCodec().decode(byteBuf));
                }
                catch(Throwable e)
                {
                    byteBuf.clear();
                    if(exception==null)
                        exception = new RuntimeException();
                    exception.addSuppressed(e);
                }
            }
            throw Objects.requireNonNull(exception);
        }
        finally
        {
            byteBuf.release();
        }
    }
    
    static <T extends Packet> T copy(T packet, ByteBufAllocator byteBufAllocator)
    {
        return packet.copy(byteBufAllocator);
    }
    
    static <T extends Packet> T copy(T packet)
    {
        return copy(packet, UnpooledByteBufAllocator.DEFAULT);
    }
}

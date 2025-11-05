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
import mz.mzlib.util.RuntimeUtil;
import mz.mzlib.util.wrapper.SpecificImpl;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@WrapMinecraftClass({@VersionName(name="net.minecraft.network.Packet", end=1904), @VersionName(name="net.minecraft.network.packet.Packet", begin=1904)})
public interface Packet extends WrapperObject
{
    WrapperFactory<Packet> FACTORY = WrapperFactory.of(Packet.class);
    
    @Deprecated
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
        return this.isInstanceOf(PacketBundleV1904.FACTORY);
    }
    
    @VersionRange(end=1700)
    @WrapMinecraftMethod(@VersionName(name="read"))
    void readV_1700(ByteBufPacket byteBuf);
    
    @VersionRange(end=2005)
    @WrapMinecraftMethod(@VersionName(name="write"))
    void writeV_2005(ByteBufPacket byteBuf);
    
    @Deprecated
    Packet copy(ByteBufAllocator byteBufAllocator);
    
    List<NetworkPhasePacketManagerV_2005> networkPhasePacketManagersV_2005 = MinecraftPlatform.instance.getVersion()<2005 ? Arrays.asList //
            ( //
                    NetworkPhasePacketManagerV_2005.play(), //
                    NetworkPhasePacketManagerV_2005.handshake(), //
                    NetworkPhasePacketManagerV_2005.login(), //
                    NetworkPhasePacketManagerV_2005.query() //
            ) : null;
    
    @SpecificImpl("copy")
    @VersionRange(end=1700)
    default Packet copyV_1700(ByteBufAllocator byteBufAllocator)
    {
        ByteBuf byteBuf = byteBufAllocator.buffer(4096);
        try
        {
            ByteBufPacket byteBufPacket = ByteBufPacket.newInstance(byteBuf);
            for(NetworkPhasePacketManagerV_2005 i: networkPhasePacketManagersV_2005)
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
                Packet result = i.createPacketV_1700(direction, id).castTo(new WrapperFactory<>(this));
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
    
    @SpecificImpl("copy")
    @VersionRange(begin=1700, end=2005)
    default Packet copyV1700_2005(ByteBufAllocator byteBufAllocator)
    {
        ByteBuf byteBuf = byteBufAllocator.buffer(4096);
        try
        {
            ByteBufPacket byteBufPacket = ByteBufPacket.newInstance(byteBuf);
            for(NetworkPhasePacketManagerV_2005 i: networkPhasePacketManagersV_2005)
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
                return i.decodePacketV1700(direction, id, byteBufPacket).castTo(new WrapperFactory<>(this));
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
                    MinecraftPlatform.instance.getVersion()<2105 ? NetworkPlaySidedPacketManagerFactoriesV2005.c2sV_2105().make(ByteBufWithRegistriesV2005.method_56350(MinecraftServer.instance.getRegistriesV1602())) : //
                            NetworkPlaySidedPacketManagerFactoriesV2005.c2sV2105().make(ByteBufWithRegistriesV2005.method_56350(MinecraftServer.instance.getRegistriesV1602()), null), //
                    NetworkHandshakeSidedPacketManagersV2005.c2s(), //
                    NetworkConfigurationSidedPacketManagersV2005.s2c(), //
                    NetworkConfigurationSidedPacketManagersV2005.c2s(), //
                    NetworkQuerySidedPacketManagersV2005.s2c(), //
                    NetworkQuerySidedPacketManagersV2005.c2s(), //
                    NetworkLoginSidedPacketManagersV2005.s2c(), //
                    NetworkLoginSidedPacketManagersV2005.c2s() //
            );
    
    @SpecificImpl("copy")
    @VersionRange(begin=2005)
    default Packet copyV2005(ByteBufAllocator byteBufAllocator) // TODO optimize
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
                    return (Packet)this.static$create(i.getCodec().decode(byteBuf));
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
    
    @Deprecated
    static <T extends Packet> T copy(T packet, ByteBufAllocator byteBufAllocator)
    {
        return RuntimeUtil.cast(packet.copy(byteBufAllocator));
    }
    
    @Deprecated
    static <T extends Packet> T copy(T packet)
    {
        return copy(packet, UnpooledByteBufAllocator.DEFAULT);
    }
}

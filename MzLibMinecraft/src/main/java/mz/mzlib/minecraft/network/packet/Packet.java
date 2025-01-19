package mz.mzlib.minecraft.network.packet;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import mz.mzlib.minecraft.MinecraftPlatform;
import mz.mzlib.minecraft.MinecraftServer;
import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.incomprehensible.network.*;
import mz.mzlib.minecraft.incomprehensible.registry.ByteBufWithRegistriesV2005;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
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
    
    <T extends Packet> T copy();
    
    @VersionRange(end=2005)
    @SpecificImpl("copy")
    default <T extends Packet> T copyV_2005()
    {
        // TODO
        throw new UnsupportedOperationException();
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
    default <T extends Packet> T copyV2005() // TODO optimize
    {
        ByteBuf byteBuf = Unpooled.buffer();
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
    
    static <T extends Packet> T copy(T packet)
    {
        return packet.copy();
    }
}

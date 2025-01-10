package mz.mzlib.minecraft.network;

import io.netty.util.concurrent.GenericFutureListener;
import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.entity.player.EntityPlayer;
import mz.mzlib.minecraft.network.listener.MinecraftPacketListener;
import mz.mzlib.minecraft.network.packet.Packet;
import mz.mzlib.minecraft.network.packet.PacketCallbacksV1901;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.SpecificImpl;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(@VersionName(name="net.minecraft.server.network.ServerPlayNetworkHandler"))
public interface ServerPlayNetworkHandler extends WrapperObject, MinecraftPacketListener
{
    @WrapperCreator
    static ServerPlayNetworkHandler create(Object object)
    {
        return WrapperObject.create(ServerPlayNetworkHandler.class, object);
    }
    
    @WrapMinecraftFieldAccessor(@VersionName(name="player"))
    EntityPlayer getPlayer();
    
    ClientConnection getConnection();
    
    @SpecificImpl("getConnection")
    @VersionRange(end=2002)
    @WrapMinecraftFieldAccessor(@VersionName(name="connection"))
    ClientConnection getConnectionV_2002();
    
    @SpecificImpl("getConnection")
    @VersionRange(begin=2002)
    default ClientConnection getConnectionV2002()
    {
        return this.castTo(ServerCommonNetworkHandlerV2002::create).getConnection();
    }
    
    void sendPacketV_2002(Packet packet);
    
    @SpecificImpl("sendPacketV_2002")
    @WrapMinecraftMethod(@VersionName(name="sendPacket", end=1400))
    void sendPacketV_1400(Packet packet);
    
    @WrapMinecraftMethod(@VersionName(name="sendPacket", begin=1400, end=1901))
    void sendPacketV1400_1901(Packet packet, GenericFutureListener<?> callbacks);
    
    @SpecificImpl("sendPacketV_2002")
    @VersionRange(begin=1400, end=1901)
    default void sendPacketImplV1400_1901(Packet packet)
    {
        this.sendPacketV1400_1901(packet, null);
    }
    
    @WrapMinecraftMethod(@VersionName(name="sendPacket", begin=1901, end=2002))
    void sendPacketV1901_2002(Packet packet, PacketCallbacksV1901 callbacks);
    
    @SpecificImpl("sendPacketV_2002")
    @VersionRange(begin=1901, end=2002)
    default void sendPacketImplV1901_2002(Packet packet)
    {
        this.sendPacketV1901_2002(packet, PacketCallbacksV1901.create(null));
    }
}

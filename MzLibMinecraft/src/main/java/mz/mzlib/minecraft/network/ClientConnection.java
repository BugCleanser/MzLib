package mz.mzlib.minecraft.network;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.concurrent.GenericFutureListener;
import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.entity.player.EntityPlayer;
import mz.mzlib.minecraft.network.packet.Packet;
import mz.mzlib.minecraft.network.packet.PacketCallbacksV1901;
import mz.mzlib.minecraft.network.packet.PacketHandler;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.minecraft.network.listener.MinecraftPacketListener;
import mz.mzlib.util.wrapper.WrapMethod;
import mz.mzlib.util.wrapper.WrapperObject;
import mz.mzlib.util.wrapper.WrapperCreator;

@WrapMinecraftClass(@VersionName(name="net.minecraft.network.ClientConnection"))
public interface ClientConnection extends WrapperObject
{
    @WrapperCreator
    static ClientConnection create(Object object)
    {
        return WrapperObject.create(ClientConnection.class, object);
    }
    
    @WrapMinecraftMethod(@VersionName(name="getPacketListener"))
    MinecraftPacketListener getPacketListener();
    
    @WrapMinecraftFieldAccessor(@VersionName(name="channel"))
    Channel getChannel();
    
    default EntityPlayer getPlayer()
    {
        MinecraftPacketListener listener = this.getPacketListener();
        if(listener.isInstanceOf(ServerPlayNetworkHandler::create))
            return ServerPlayNetworkHandler.create(listener.getWrapped()).getPlayer();
        return EntityPlayer.create(null);
    }
    
    @WrapMinecraftMethod(@VersionName(name="send"))
    void sendPacket(Packet packet);
    
    @VersionRange(end=1300)
    @WrapMinecraftMethod(@VersionName(name="sendImmediately"))
    void sendPacketImmediatelyV_1300(Packet packet, GenericFutureListener<?>[] callbacks);
    
    @VersionRange(begin=1300, end=1901)
    @WrapMinecraftMethod({@VersionName(name="method_20161", end=1400), @VersionName(name="sendImmediately", begin=1400)})
    void sendPacketImmediatelyV1300_1901(Packet packet, GenericFutureListener<?> callbacks);
    
    @VersionRange(begin=1901, end=2002)
    @WrapMinecraftMethod(@VersionName(name="sendImmediately"))
    void sendPacketImmediatelyV1901_2002(Packet packet, PacketCallbacksV1901 callbacks);
    
    @VersionRange(begin=2002)
    @WrapMinecraftMethod(@VersionName(name="sendImmediately"))
    void sendPacketImmediatelyV2002(Packet packet, PacketCallbacksV1901 callbacks, boolean flush);
    
    @WrapMethod("@0")
    void channelRead0(ChannelHandlerContext ctx, Packet packet);
    
    static void handlePacket(Packet packet, PacketHandler handler)
    {
        create(null).staticHandlePacket(packet, handler);
    }
    @WrapMinecraftMethod({@VersionName(name="method_20159", end=1400), @VersionName(name="handlePacket", begin=1400)})
    void staticHandlePacket(Packet packet, PacketHandler handler);
    
    @WrapMinecraftMethod(@VersionName(name="handleDisconnection"))
    void handleDisconnection();
}

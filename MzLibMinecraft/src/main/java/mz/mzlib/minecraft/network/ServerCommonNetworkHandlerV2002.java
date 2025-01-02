package mz.mzlib.minecraft.network;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.entity.player.EntityPlayer;
import mz.mzlib.minecraft.network.listener.MinecraftPacketListener;
import mz.mzlib.minecraft.network.packet.Packet;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.SpecificImpl;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(@VersionName(name="net.minecraft.server.network.ServerCommonNetworkHandler", begin=2002))
public interface ServerCommonNetworkHandlerV2002 extends WrapperObject, MinecraftPacketListener
{
    @WrapperCreator
    static ServerCommonNetworkHandlerV2002 create(Object object)
    {
        return WrapperObject.create(ServerCommonNetworkHandlerV2002.class, object);
    }
    
    @WrapMinecraftFieldAccessor(@VersionName(name="connection"))
    ClientConnection getConnection();
    
    default EntityPlayer getPlayer()
    {
        if(this.isInstanceOf(ServerPlayNetworkHandler::create))
            return this.castTo(ServerPlayNetworkHandler::create).getPlayer();
        return null;
    }
    
    @WrapMinecraftMethod(@VersionName(name="sendPacket"))
    void sendPacket(Packet packet);
}

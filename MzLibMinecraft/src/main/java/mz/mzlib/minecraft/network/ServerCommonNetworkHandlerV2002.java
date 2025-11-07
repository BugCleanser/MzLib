package mz.mzlib.minecraft.network;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.entity.player.EntityPlayer;
import mz.mzlib.minecraft.network.listener.MinecraftPacketListener;
import mz.mzlib.minecraft.network.packet.Packet;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(@VersionName(name = "net.minecraft.server.network.ServerCommonNetworkHandler", begin = 2002))
public interface ServerCommonNetworkHandlerV2002 extends WrapperObject, MinecraftPacketListener
{
    WrapperFactory<ServerCommonNetworkHandlerV2002> FACTORY = WrapperFactory.of(ServerCommonNetworkHandlerV2002.class);
    @Deprecated
    @WrapperCreator
    static ServerCommonNetworkHandlerV2002 create(Object wrapped)
    {
        return WrapperObject.create(ServerCommonNetworkHandlerV2002.class, wrapped);
    }

    @WrapMinecraftFieldAccessor(@VersionName(name = "connection"))
    ClientConnection getConnection();

    default EntityPlayer getPlayer()
    {
        if(this.isInstanceOf(ServerPlayNetworkHandler.FACTORY))
            return this.castTo(ServerPlayNetworkHandler.FACTORY).getPlayer();
        return null;
    }

    @WrapMinecraftMethod(@VersionName(name = "sendPacket"))
    void sendPacket(Packet packet);
}

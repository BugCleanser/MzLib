package mz.mzlib.minecraft.network;

import mz.mzlib.minecraft.MinecraftPlatform;
import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.minecraft.entity.player.EntityPlayer;
import mz.mzlib.minecraft.network.listener.MinecraftPacketListener;
import mz.mzlib.util.wrapper.WrapperObject;
import mz.mzlib.util.wrapper.WrapperCreator;

@WrapMinecraftClass(@VersionName(name = "net.minecraft.server.network.ServerPlayNetworkHandler"))
public interface ServerPlayNetworkHandler extends MinecraftPacketListener
{
    @WrapperCreator
    static ServerPlayNetworkHandler create(Object object)
    {
        return WrapperObject.create(ServerPlayNetworkHandler.class, object);
    }

    @WrapMinecraftFieldAccessor(@VersionName(name = "player"))
    EntityPlayer getPlayer();

    default ClientConnection getConnection()
    {
        if(MinecraftPlatform.instance.getVersion()<2001)
            return this.getConnectionV_2001();
        else
            return this.castTo(ServerCommonNetworkHandlerV2001::create).getConnection();
    }

    @WrapMinecraftFieldAccessor(@VersionName(name = "connection", end=2001))
    ClientConnection getConnectionV_2001();
}

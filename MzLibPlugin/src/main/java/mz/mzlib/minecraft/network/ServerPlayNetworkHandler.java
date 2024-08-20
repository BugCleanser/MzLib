package mz.mzlib.minecraft.network;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.minecraft.entity.EntityPlayer;
import mz.mzlib.minecraft.network.listener.MinecraftPacketListener;
import mz.mzlib.util.wrapper.WrapperObject;
import mz.mzlib.util.wrapper.WrapperCreator;

@WrapMinecraftClass(@VersionName(name = "net.minecraft.server.network.ServerPlayNetworkHandler"))
public interface ServerPlayNetworkHandler extends MinecraftPacketListener
{
    @SuppressWarnings("deprecation")
    @WrapperCreator
    static ServerPlayNetworkHandler create(Object object)
    {
        return WrapperObject.create(ServerPlayNetworkHandler.class, object);
    }

    @WrapMinecraftFieldAccessor(@VersionName(name = "player"))
    EntityPlayer getPlayer();

    @WrapMinecraftFieldAccessor(@VersionName(name = "connection"))
    ClientConnection getConnection();
}

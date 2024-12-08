package mz.mzlib.minecraft.network;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.entity.player.EntityPlayer;
import mz.mzlib.minecraft.network.listener.MinecraftPacketListener;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.util.wrapper.SpecificImpl;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

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

    ClientConnection getConnection();
    @SpecificImpl("getConnection")
    @WrapMinecraftFieldAccessor(@VersionName(name = "connection", end=2001))
    ClientConnection getConnectionV_2001();
    @SpecificImpl("getConnection")
    @VersionRange(begin=2001)
    default ClientConnection getConnectionV2001()
    {
        return this.castTo(ServerCommonNetworkHandlerV2001::create).getConnection();
    }
}

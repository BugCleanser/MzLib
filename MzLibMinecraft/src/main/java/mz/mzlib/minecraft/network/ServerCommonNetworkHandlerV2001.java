package mz.mzlib.minecraft.network;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.network.listener.MinecraftPacketListener;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(@VersionName(name = "net.minecraft.server.network.ServerCommonNetworkHandler", begin=2001))
public interface ServerCommonNetworkHandlerV2001 extends MinecraftPacketListener
{
    @WrapperCreator
    static ServerCommonNetworkHandlerV2001 create(Object object)
    {
        return WrapperObject.create(ServerCommonNetworkHandlerV2001.class, object);
    }

    @WrapMinecraftFieldAccessor(@VersionName(name = "connection"))
    ClientConnection getConnection();
}

package mz.mzlib.minecraft.network;

import io.netty.channel.Channel;
import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.minecraft.network.listener.MinecraftPacketListener;
import mz.mzlib.util.wrapper.WrapperObject;
import mz.mzlib.util.wrapper.WrapperCreator;

@WrapMinecraftClass(@VersionName(name="net.minecraft.network.ClientConnection"))
public interface ClientConnection extends WrapperObject
{
    @SuppressWarnings("deprecation")
    @WrapperCreator
    static ClientConnection create(Object object)
    {
        return WrapperObject.create(ClientConnection.class, object);
    }

    @WrapMinecraftMethod(@VersionName(name = "getPacketListener"))
    MinecraftPacketListener getPacketListener();

    @WrapMinecraftFieldAccessor(@VersionName(name = "channel"))
    Channel getChannel();
}

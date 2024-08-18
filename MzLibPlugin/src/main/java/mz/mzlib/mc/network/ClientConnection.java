package mz.mzlib.mc.network;

import io.netty.channel.Channel;
import mz.mzlib.mc.VersionName;
import mz.mzlib.mc.delegator.DelegatorMinecraftClass;
import mz.mzlib.mc.delegator.DelegatorMinecraftFieldAccessor;
import mz.mzlib.mc.delegator.DelegatorMinecraftMethod;
import mz.mzlib.mc.network.listener.MinecraftPacketListener;
import mz.mzlib.util.delegator.Delegator;
import mz.mzlib.util.delegator.DelegatorCreator;

@DelegatorMinecraftClass(@VersionName(name="net.minecraft.network.ClientConnection"))
public interface ClientConnection extends Delegator
{
    @SuppressWarnings("deprecation")
    @DelegatorCreator
    static ClientConnection create(Object object)
    {
        return Delegator.create(ClientConnection.class, object);
    }

    @DelegatorMinecraftMethod(@VersionName(name = "getPacketListener"))
    MinecraftPacketListener getPacketListener();

    @DelegatorMinecraftFieldAccessor(@VersionName(name = "channel"))
    Channel getChannel();
}

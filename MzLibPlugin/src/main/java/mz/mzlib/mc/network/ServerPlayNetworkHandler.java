package mz.mzlib.mc.network;

import mz.mzlib.mc.VersionName;
import mz.mzlib.mc.delegator.DelegatorMinecraftClass;
import mz.mzlib.mc.delegator.DelegatorMinecraftFieldAccessor;
import mz.mzlib.mc.entity.EntityPlayer;
import mz.mzlib.mc.network.listener.MinecraftPacketListener;
import mz.mzlib.util.delegator.Delegator;
import mz.mzlib.util.delegator.DelegatorCreator;

@DelegatorMinecraftClass(@VersionName(name = "net.minecraft.server.network.ServerPlayNetworkHandler"))
public interface ServerPlayNetworkHandler extends MinecraftPacketListener
{
    @SuppressWarnings("deprecation")
    @DelegatorCreator
    static ServerPlayNetworkHandler create(Object object)
    {
        return Delegator.create(ServerPlayNetworkHandler.class, object);
    }

    @DelegatorMinecraftFieldAccessor(@VersionName(name = "player"))
    EntityPlayer getPlayer();

    @DelegatorMinecraftFieldAccessor(@VersionName(name = "connection"))
    ClientConnection getConnection();
}

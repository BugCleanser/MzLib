package mz.mzlib.mc.entity;

import mz.mzlib.mc.VersionName;
import mz.mzlib.mc.delegator.DelegatorMinecraftClass;
import mz.mzlib.mc.delegator.DelegatorMinecraftFieldAccessor;
import mz.mzlib.mc.network.ServerPlayNetworkHandler;
import mz.mzlib.util.delegator.Delegator;
import mz.mzlib.util.delegator.DelegatorCreator;

@DelegatorMinecraftClass({@VersionName(end=1400,name="net.minecraft.entity.player.ServerPlayerEntity"),@VersionName(begin = 1400, name = "net.minecraft.server.network.ServerPlayerEntity")})
public interface EntityPlayer extends Entity
{
    @SuppressWarnings("deprecation")
    @DelegatorCreator
    static EntityPlayer create(Object delegate)
    {
        return Delegator.create(EntityPlayer.class, delegate);
    }

    @DelegatorMinecraftFieldAccessor(@VersionName(name = "networkHandler"))
    ServerPlayNetworkHandler getNetworkHandler();
}

package mz.mzlib.minecraft.entity;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.minecraft.network.ServerPlayNetworkHandler;
import mz.mzlib.util.wrapper.WrapperObject;
import mz.mzlib.util.wrapper.WrapperCreator;

@WrapMinecraftClass({@VersionName(end=1400,name="net.minecraft.entity.player.ServerPlayerEntity"),@VersionName(begin = 1400, name = "net.minecraft.server.network.ServerPlayerEntity")})
public interface EntityPlayer extends Entity
{
    @SuppressWarnings("deprecation")
    @WrapperCreator
    static EntityPlayer create(Object wrapped)
    {
        return WrapperObject.create(EntityPlayer.class, wrapped);
    }

    @WrapMinecraftFieldAccessor(@VersionName(name = "networkHandler"))
    ServerPlayNetworkHandler getNetworkHandler();
}

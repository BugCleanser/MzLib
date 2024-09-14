package mz.mzlib.minecraft.entity.player;

import mz.mzlib.minecraft.MinecraftPlatform;
import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.entity.EntityLiving;
import mz.mzlib.minecraft.network.ServerPlayNetworkHandler;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass({@VersionName(end=1400,name="net.minecraft.entity.player.ServerPlayerEntity"),@VersionName(begin = 1400, name = "net.minecraft.server.network.ServerPlayerEntity")})
public interface EntityPlayer extends EntityLiving
{
    @SuppressWarnings("deprecation")
    @WrapperCreator
    static EntityPlayer create(Object wrapped)
    {
        return WrapperObject.create(EntityPlayer.class, wrapped);
    }

    @WrapMinecraftFieldAccessor(@VersionName(name = "networkHandler"))
    ServerPlayNetworkHandler getNetworkHandler();

    @WrapMinecraftFieldAccessor({@VersionName(name = "language", end=1400), @VersionName(name = "clientLanguage", begin=1400, end=1600), @VersionName(name = "language", begin=2002)})
    String getLanguageV_1600__2002();

    default String getLanguage()
    {
        return MinecraftPlatform.instance.getLanguage(this);
    }
}
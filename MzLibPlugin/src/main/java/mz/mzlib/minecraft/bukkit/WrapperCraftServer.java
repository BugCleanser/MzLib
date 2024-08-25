package mz.mzlib.minecraft.bukkit;

import mz.mzlib.minecraft.MinecraftServer;
import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.bukkit.wrapper.WrapCraftbukkitClass;
import mz.mzlib.util.wrapper.WrapperObject;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapMethod;

@WrapCraftbukkitClass(@VersionName(name = "OBC.CraftServer"))
public interface WrapperCraftServer extends WrapperObject
{
    @WrapperCreator
    static WrapperCraftServer create(Object wrapped)
    {
        return WrapperObject.create(WrapperCraftServer.class, wrapped);
    }

    @WrapMethod("getServer")
    MinecraftServer getServer();
}

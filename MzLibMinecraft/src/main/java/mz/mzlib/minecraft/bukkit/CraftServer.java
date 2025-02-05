package mz.mzlib.minecraft.bukkit;

import mz.mzlib.minecraft.MinecraftServer;
import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.bukkit.wrapper.WrapCraftbukkitClass;
import mz.mzlib.util.wrapper.WrapperObject;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapMethod;

@BukkitEnabled
@WrapCraftbukkitClass(@VersionName(name = "OBC.CraftServer"))
public interface CraftServer extends WrapperObject
{
    @WrapperCreator
    static CraftServer create(Object wrapped)
    {
        return WrapperObject.create(CraftServer.class, wrapped);
    }

    @WrapMethod("getServer")
    MinecraftServer getServer();
}

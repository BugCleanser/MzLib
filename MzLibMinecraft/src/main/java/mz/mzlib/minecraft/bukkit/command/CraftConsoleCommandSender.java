package mz.mzlib.minecraft.bukkit.command;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.bukkit.BukkitEnabled;
import mz.mzlib.minecraft.bukkit.wrapper.WrapCraftbukkitClass;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@BukkitEnabled
@WrapCraftbukkitClass(@VersionName(name="OBC.command.CraftConsoleCommandSender"))
public interface CraftConsoleCommandSender extends WrapperObject
{
    @WrapperCreator
    static CraftConsoleCommandSender create(Object wrapped)
    {
        return WrapperObject.create(CraftConsoleCommandSender.class, wrapped);
    }
}

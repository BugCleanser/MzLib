package mz.mzlib.minecraft.bukkit.command;

import mz.mzlib.minecraft.MinecraftPlatform;
import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.bukkit.BukkitEnabled;
import mz.mzlib.minecraft.bukkit.wrapper.WrapCraftbukkitClass;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@MinecraftPlatform.Enabled(MinecraftPlatform.Tag.BUKKIT)
@WrapCraftbukkitClass(@VersionName(name="OBC.command.CraftConsoleCommandSender"))
public interface CraftConsoleCommandSender extends WrapperObject
{
    WrapperFactory<CraftConsoleCommandSender> FACTORY = WrapperFactory.of(CraftConsoleCommandSender.class);
    @Deprecated
    @WrapperCreator
    static CraftConsoleCommandSender create(Object wrapped)
    {
        return WrapperObject.create(CraftConsoleCommandSender.class, wrapped);
    }
}

package mz.mzlib.minecraft.bukkit.command;

import mz.mzlib.minecraft.command.CommandSource;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

public class BukkitCommandSourceUtil
{
    public static CommandSender toBukkit(CommandSource object)
    {
        return ((CommandSender)object.castTo(CommandSourceBukkit::create).getBukkitSender().getWrapped());
    }
    
    public static CommandSource fromBukkit(CommandSender object)
    {
        return CraftCommandVanillaWrapper.toCommandSource(object);
    }
}

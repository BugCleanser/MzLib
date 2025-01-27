package mz.mzlib.minecraft.bukkit.command;

import mz.mzlib.minecraft.command.CommandSource;
import org.bukkit.command.CommandSender;

public class BukkitCommandSourceUtil
{
    public static CommandSender toBukkit(CommandSource object)
    {
        return object.castTo(CommandSourceBukkit::create).getBukkitSender();
    }
    
    public static CommandSource fromBukkit(CommandSender object)
    {
        return CraftCommandVanillaWrapper.toCommandSource(object);
    }
}

package mz.mzlib.minecraft.bukkit.command;

import mz.mzlib.minecraft.MinecraftPlatform;
import mz.mzlib.minecraft.MinecraftServer;
import mz.mzlib.minecraft.bukkit.entity.BukkitEntityUtil;
import mz.mzlib.minecraft.command.CommandSender;
import mz.mzlib.minecraft.entity.Entity;
import org.bukkit.Bukkit;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.ConsoleCommandSender;

public class BukkitCommandSenderUtil
{
    public static CommandSender fromBukkit(org.bukkit.command.CommandSender object)
    {
        if(object instanceof org.bukkit.entity.Entity)
            return BukkitEntityUtil.fromBukkit((org.bukkit.entity.Entity) object);
        else if(object instanceof ConsoleCommandSender)
            return MinecraftServer.instance;
        else if(object instanceof BlockCommandSender)
        {
            if(MinecraftPlatform.instance.getVersion()>=1400)
                return CraftBlockCommandSender.create(object).getCommandSourceV1400().getSender();
        }
        // TODO
        throw new UnsupportedOperationException(object.toString());
    }
    
    public static org.bukkit.command.CommandSender toBukkit(CommandSender object)
    {
        if(object.isInstanceOf(Entity::create))
            return BukkitEntityUtil.toBukkit(object.castTo(Entity::create));
        else if(object.isInstanceOf(MinecraftServer::create))
            return Bukkit.getConsoleSender();
        // TODO
        throw new UnsupportedOperationException(object.toString());
    }
}

package mz.mzlib.minecraft.bukkit;

import mz.mzlib.minecraft.GameObject;
import mz.mzlib.minecraft.MinecraftServer;
import mz.mzlib.minecraft.bukkit.command.CraftBlockCommandSender;
import mz.mzlib.minecraft.bukkit.entity.CraftEntity;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Entity;

public class BukkitGameObjectUtil
{
    public static GameObject fromBukkit(CommandSender object)
    {
        if(object instanceof Entity)
            return CraftEntity.create(object).getHandle();
        else if(object instanceof ConsoleCommandSender)
            return MinecraftServer.instance;
        else if(object instanceof BlockCommandSender)
            return CraftBlockCommandSender.create(object).getCommandSource().getGameObject();
        // TODO
        throw new UnsupportedOperationException(object.toString());
    }
}

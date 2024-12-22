package mz.mzlib.minecraft.bukkit;

import mz.mzlib.minecraft.CommandSender;
import mz.mzlib.minecraft.MinecraftPlatform;
import mz.mzlib.minecraft.MinecraftServer;
import mz.mzlib.minecraft.bukkit.command.CraftBlockCommandSender;
import mz.mzlib.minecraft.bukkit.entity.CraftEntity;
import mz.mzlib.minecraft.bukkit.entity.EntityBukkit;
import mz.mzlib.minecraft.entity.Entity;
import org.bukkit.Bukkit;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.ConsoleCommandSender;

public class BukkitCommandSenderUtil
{
    public static CommandSender fromBukkit(org.bukkit.command.CommandSender object)
    {
        if(object instanceof org.bukkit.entity.Entity)
            return CraftEntity.create(object).getHandle();
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
            return object.castTo(EntityBukkit::create).getBukkitEntity().getWrapped();
        else if(object.isInstanceOf(MinecraftServer::create))
            return Bukkit.getConsoleSender();
        // TODO
        throw new UnsupportedOperationException(object.toString());
    }
}

package mz.mzlib.minecraft.bukkit.command;

import mz.mzlib.minecraft.MinecraftServer;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.bukkit.BukkitOnly;
import mz.mzlib.minecraft.bukkit.MinecraftServerBukkit;
import mz.mzlib.minecraft.bukkit.entity.BukkitEntityUtil;
import mz.mzlib.minecraft.command.CommandSource;
import mz.mzlib.minecraft.command.RconConsole;
import mz.mzlib.util.wrapper.*;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

@BukkitOnly
@WrapSameClass(CommandSource.class)
public interface CommandSourceBukkit extends CommandSource
{
    @WrapperCreator
    static CommandSourceBukkit create(Object wrapped)
    {
        return WrapperObject.create(CommandSourceBukkit.class, wrapped);
    }
    
    CommandSender getBukkitSender();
    
    @SpecificImpl("getBukkitSender")
    @VersionRange(end=1200)
    default CommandSender getBukkitSenderV_1200()
    {
        if(this.getEntity().isPresent())
            return BukkitEntityUtil.toBukkit(this.getEntity());
        else if(this.isInstanceOf(MinecraftServer::create))
            return Bukkit.getConsoleSender();
        else if(this.isInstanceOf(CommandBlockExecutorBukkit::create))
            return this.castTo(CommandBlockExecutorBukkit::create).getBukkitSenderV_1300();
        else if(this.isInstanceOf(RconConsole::create))
            return MinecraftServer.instance.castTo(MinecraftServerBukkit::create).getRemoteConsoleV_2002();
        else
            throw new UnsupportedOperationException();
    }
    
    @SpecificImpl("getBukkitSender")
    @VersionRange(begin=1200, end=1300)
    default CommandSender getBukkitSenderV1200_1300()
    {
        return CommandBlockExecutorBukkit.commandSourceToBukkitV1200_1300(this);
    }
    
    @SpecificImpl("getBukkitSender")
    @VersionRange(begin=1300)
    @WrapMethod("getBukkitSender")
    CommandSender getBukkitSenderV1300();
}

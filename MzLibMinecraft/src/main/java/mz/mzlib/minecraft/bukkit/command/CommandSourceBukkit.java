package mz.mzlib.minecraft.bukkit.command;

import mz.mzlib.minecraft.MinecraftServer;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.bukkit.BukkitEnabled;
import mz.mzlib.minecraft.bukkit.MinecraftServerBukkit;
import mz.mzlib.minecraft.bukkit.entity.BukkitEntityUtil;
import mz.mzlib.minecraft.command.CommandSource;
import mz.mzlib.minecraft.command.RconConsole;
import mz.mzlib.minecraft.entity.Entity;
import mz.mzlib.util.wrapper.*;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

@BukkitEnabled
@WrapSameClass(CommandSource.class)
public interface CommandSourceBukkit extends CommandSource
{
    WrapperFactory<CommandSourceBukkit> FACTORY = WrapperFactory.find(CommandSourceBukkit.class);
    @Deprecated
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
        for(Entity entity: this.getEntity())
            return BukkitEntityUtil.toBukkit(entity);
        if(this.isInstanceOf(MinecraftServer.FACTORY))
            return Bukkit.getConsoleSender();
        if(this.isInstanceOf(CommandBlockExecutorBukkit.FACTORY))
            return this.castTo(CommandBlockExecutorBukkit.FACTORY).getBukkitSenderV_1300();
        if(this.isInstanceOf(RconConsole.FACTORY))
            return MinecraftServer.instance.castTo(MinecraftServerBukkit.FACTORY).getRemoteConsoleV_2002();
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

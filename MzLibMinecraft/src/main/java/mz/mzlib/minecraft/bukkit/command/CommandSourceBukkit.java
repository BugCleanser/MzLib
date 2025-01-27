package mz.mzlib.minecraft.bukkit.command;

import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.bukkit.BukkitOnly;
import mz.mzlib.minecraft.command.CommandSource;
import mz.mzlib.util.wrapper.*;
import org.bukkit.command.CommandSender;

@BukkitOnly
@WrapSameClass(CommandSource.class)
public interface CommandSourceBukkit extends WrapperObject, CommandSource
{
    @WrapperCreator
    static CommandSourceBukkit create(Object wrapped)
    {
        return WrapperObject.create(CommandSourceBukkit.class, wrapped);
    }
    
    CommandSender getBukkitSender();
    @SpecificImpl("getBukkitSender")
    @VersionRange(end=1300)
    default CommandSender getBukkitSenderV_1300()
    {
        return CommandBlockExecutorBukkit.commandSourceToBukkitV_1300(this);
    }
    @SpecificImpl("getBukkitSender")
    @VersionRange(begin=1300)
    @WrapMethod("getBukkitSender")
    CommandSender getBukkitSenderV1300();
}

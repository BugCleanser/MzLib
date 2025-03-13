package mz.mzlib.minecraft.bukkit.command;

import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.bukkit.BukkitEnabled;
import mz.mzlib.minecraft.command.CommandBlockExecutor;
import mz.mzlib.minecraft.command.CommandSource;
import mz.mzlib.util.wrapper.*;
import org.bukkit.command.CommandSender;

@BukkitEnabled
@WrapSameClass(CommandBlockExecutor.class)
public interface CommandBlockExecutorBukkit extends WrapperObject, CommandBlockExecutor
{
    WrapperFactory<CommandBlockExecutorBukkit> FACTORY = WrapperFactory.find(CommandBlockExecutorBukkit.class);
    @Deprecated
    @WrapperCreator
    static CommandBlockExecutorBukkit create(Object wrapped)
    {
        return WrapperObject.create(CommandBlockExecutorBukkit.class, wrapped);
    }
    
    @VersionRange(begin=1200, end=1300)
    static CommandSender commandSourceToBukkitV1200_1300(CommandSource source)
    {
        return create(null).staticCommandSourceToBukkitV1200_1300(source);
    }
    @VersionRange(begin=1200, end=1300)
    @WrapMethod("unwrapSender")
    CommandSender staticCommandSourceToBukkitV1200_1300(CommandSource source);
    
    @VersionRange(end=1300)
    @WrapFieldAccessor("sender")
    CommandSender getBukkitSenderV_1300();
}

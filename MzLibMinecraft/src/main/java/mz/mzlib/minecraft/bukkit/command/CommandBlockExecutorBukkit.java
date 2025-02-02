package mz.mzlib.minecraft.bukkit.command;

import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.bukkit.BukkitOnly;
import mz.mzlib.minecraft.command.CommandBlockExecutor;
import mz.mzlib.minecraft.command.CommandSource;
import mz.mzlib.util.wrapper.*;
import org.bukkit.command.CommandSender;

@BukkitOnly
@WrapSameClass(CommandBlockExecutor.class)
public interface CommandBlockExecutorBukkit extends WrapperObject, CommandBlockExecutor
{
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
    
    @WrapFieldAccessor("sender")
    CommandSender getBukkitSender();
}

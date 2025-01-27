package mz.mzlib.minecraft.bukkit.command;

import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.command.CommandBlockExecutor;
import mz.mzlib.minecraft.command.CommandSource;
import mz.mzlib.util.wrapper.WrapMethod;
import mz.mzlib.util.wrapper.WrapSameClass;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;
import org.bukkit.command.CommandSender;

@WrapSameClass(CommandBlockExecutor.class)
public interface CommandBlockExecutorBukkit extends WrapperObject, CommandBlockExecutor
{
    @WrapperCreator
    static CommandBlockExecutorBukkit create(Object wrapped)
    {
        return WrapperObject.create(CommandBlockExecutorBukkit.class, wrapped);
    }
    
    @VersionRange(end=1300)
    static CommandSender commandSourceToBukkitV_1300(CommandSource source)
    {
        return create(null).staticCommandSourceToBukkitV_1300(source);
    }
    @VersionRange(end=1300)
    @WrapMethod("unwrapSender")
    CommandSender staticCommandSourceToBukkitV_1300(CommandSource source);
}

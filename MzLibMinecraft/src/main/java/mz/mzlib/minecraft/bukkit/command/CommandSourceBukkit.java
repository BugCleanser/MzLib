package mz.mzlib.minecraft.bukkit.command;

import mz.mzlib.minecraft.bukkit.BukkitOnly;
import mz.mzlib.minecraft.command.CommandSource;
import mz.mzlib.util.wrapper.WrapMethod;
import mz.mzlib.util.wrapper.WrapSameClass;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@BukkitOnly
@WrapSameClass(CommandSource.class)
public interface CommandSourceBukkit extends WrapperObject, CommandSource
{
    @WrapperCreator
    static CommandSourceBukkit create(Object wrapped)
    {
        return WrapperObject.create(CommandSourceBukkit.class, wrapped);
    }
    
    @WrapMethod("getBukkitSender")
    BukkitCommandSender getBukkitSender();
}

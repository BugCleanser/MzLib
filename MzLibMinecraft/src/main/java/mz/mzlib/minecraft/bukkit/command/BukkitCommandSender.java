package mz.mzlib.minecraft.bukkit.command;

import mz.mzlib.minecraft.bukkit.BukkitOnly;
import mz.mzlib.util.wrapper.WrapClassForName;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@BukkitOnly
@WrapClassForName("org.bukkit.command.CommandSender")
public interface BukkitCommandSender extends WrapperObject
{
    @WrapperCreator
    static BukkitCommandSender create(Object wrapped)
    {
        return WrapperObject.create(BukkitCommandSender.class, wrapped);
    }
}

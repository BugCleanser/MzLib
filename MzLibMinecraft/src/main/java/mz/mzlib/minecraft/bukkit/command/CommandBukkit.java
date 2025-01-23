package mz.mzlib.minecraft.bukkit.command;

import mz.mzlib.minecraft.bukkit.BukkitOnly;
import mz.mzlib.util.wrapper.WrapClassForName;
import mz.mzlib.util.wrapper.WrapConstructor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.Plugin;

@BukkitOnly
@WrapClassForName("org.bukkit.command.PluginCommand")
public interface CommandBukkit extends WrapperObject
{
    @WrapperCreator
    static CommandBukkit create(Object wrapped)
    {
        return WrapperObject.create(CommandBukkit.class, wrapped);
    }
    
    @Override
    PluginCommand getWrapped();
    
    static CommandBukkit newInstance(String name, Plugin owner)
    {
        return create(null).staticNewInstance(name, owner);
    }
    @WrapConstructor
    CommandBukkit staticNewInstance(String name, Plugin owner);
}

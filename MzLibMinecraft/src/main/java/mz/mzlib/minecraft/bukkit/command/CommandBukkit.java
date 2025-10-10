package mz.mzlib.minecraft.bukkit.command;

import mz.mzlib.minecraft.bukkit.BukkitEnabled;
import mz.mzlib.util.wrapper.*;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.Plugin;

@BukkitEnabled
@WrapClassForName("org.bukkit.command.PluginCommand")
public interface CommandBukkit extends WrapperObject
{
    WrapperFactory<CommandBukkit> FACTORY = WrapperFactory.of(CommandBukkit.class);
    @Deprecated
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

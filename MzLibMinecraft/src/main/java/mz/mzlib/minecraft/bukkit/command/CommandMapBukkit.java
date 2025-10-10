package mz.mzlib.minecraft.bukkit.command;

import mz.mzlib.minecraft.bukkit.BukkitEnabled;
import mz.mzlib.util.wrapper.*;
import org.bukkit.command.SimpleCommandMap;

import java.util.Map;

@BukkitEnabled
@WrapClassForName("org.bukkit.command.SimpleCommandMap")
public interface CommandMapBukkit extends WrapperObject
{
    WrapperFactory<CommandMapBukkit> FACTORY = WrapperFactory.of(CommandMapBukkit.class);
    @Deprecated
    @WrapperCreator
    static CommandMapBukkit create(Object wrapped)
    {
        return WrapperObject.create(CommandMapBukkit.class, wrapped);
    }
    
    @Override
    SimpleCommandMap getWrapped();
    
    @WrapFieldAccessor("knownCommands")
    Map<String, org.bukkit.command.Command> getCommands();
}

package mz.mzlib.minecraft.bukkit.command;

import mz.mzlib.minecraft.bukkit.BukkitEnabled;
import mz.mzlib.util.wrapper.WrapClassForName;
import mz.mzlib.util.wrapper.WrapFieldAccessor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;
import org.bukkit.command.SimpleCommandMap;

import java.util.Map;

@BukkitEnabled
@WrapClassForName("org.bukkit.command.SimpleCommandMap")
public interface CommandMapBukkit extends WrapperObject
{
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

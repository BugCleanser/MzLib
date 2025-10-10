package mz.mzlib.minecraft.bukkit.command;

import mz.mzlib.minecraft.bukkit.BukkitEnabled;
import mz.mzlib.util.wrapper.*;

@BukkitEnabled
@WrapClassForName("org.bukkit.plugin.SimplePluginManager")
public interface PluginManagerBukkit extends WrapperObject
{
    WrapperFactory<PluginManagerBukkit> FACTORY = WrapperFactory.of(PluginManagerBukkit.class);
    @Deprecated
    @WrapperCreator
    static PluginManagerBukkit create(Object wrapped)
    {
        return WrapperObject.create(PluginManagerBukkit.class, wrapped);
    }
    
    @WrapFieldAccessor("commandMap")
    CommandMapBukkit getCommandMap();
}

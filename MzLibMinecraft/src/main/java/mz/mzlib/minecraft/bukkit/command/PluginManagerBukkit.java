package mz.mzlib.minecraft.bukkit.command;

import mz.mzlib.minecraft.bukkit.BukkitOnly;
import mz.mzlib.util.wrapper.WrapClassForName;
import mz.mzlib.util.wrapper.WrapFieldAccessor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@BukkitOnly
@WrapClassForName("org.bukkit.plugin.SimplePluginManager")
public interface PluginManagerBukkit extends WrapperObject
{
    @WrapperCreator
    static PluginManagerBukkit create(Object wrapped)
    {
        return WrapperObject.create(PluginManagerBukkit.class, wrapped);
    }
    
    @WrapFieldAccessor("commandMap")
    CommandMapBukkit getCommandMap();
}

package mz.mzlib.minecraft.bukkit.command;

import mz.mzlib.minecraft.MinecraftPlatform;
import mz.mzlib.util.wrapper.WrapClassForName;
import mz.mzlib.util.wrapper.WrapFieldAccessor;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@MinecraftPlatform.Enabled(MinecraftPlatform.Tag.BUKKIT)
@WrapClassForName("org.bukkit.plugin.SimplePluginManager")
public interface PluginManagerBukkit extends WrapperObject
{
    WrapperFactory<PluginManagerBukkit> FACTORY = WrapperFactory.of(PluginManagerBukkit.class);
    @WrapFieldAccessor("commandMap")
    CommandMapBukkit getCommandMap();
}


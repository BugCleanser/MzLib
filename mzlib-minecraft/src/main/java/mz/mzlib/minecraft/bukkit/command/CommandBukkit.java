package mz.mzlib.minecraft.bukkit.command;

import mz.mzlib.minecraft.MinecraftPlatform;
import mz.mzlib.util.wrapper.WrapClassForName;
import mz.mzlib.util.wrapper.WrapConstructor;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.Plugin;

@MinecraftPlatform.Enabled(MinecraftPlatform.Tag.BUKKIT)
@WrapClassForName("org.bukkit.command.PluginCommand")
public interface CommandBukkit extends WrapperObject
{
    WrapperFactory<CommandBukkit> FACTORY = WrapperFactory.of(CommandBukkit.class);
    @Override
    PluginCommand getWrapped();

    static CommandBukkit newInstance(String name, Plugin owner)
    {
        return FACTORY.getStatic().static$newInstance(name, owner);
    }
    @WrapConstructor
    CommandBukkit static$newInstance(String name, Plugin owner);
}


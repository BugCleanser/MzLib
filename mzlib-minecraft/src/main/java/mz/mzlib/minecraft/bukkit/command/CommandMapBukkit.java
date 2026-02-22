package mz.mzlib.minecraft.bukkit.command;

import mz.mzlib.minecraft.MinecraftPlatform;
import mz.mzlib.util.wrapper.WrapClassForName;
import mz.mzlib.util.wrapper.WrapFieldAccessor;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;
import org.bukkit.command.SimpleCommandMap;

import java.util.Map;

@MinecraftPlatform.Enabled(MinecraftPlatform.Tag.BUKKIT)
@WrapClassForName("org.bukkit.command.SimpleCommandMap")
public interface CommandMapBukkit extends WrapperObject
{
    WrapperFactory<CommandMapBukkit> FACTORY = WrapperFactory.of(CommandMapBukkit.class);
    @Override
    SimpleCommandMap getWrapped();

    @WrapFieldAccessor("knownCommands")
    Map<String, org.bukkit.command.Command> getCommands();
}


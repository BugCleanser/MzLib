package mz.mzlib.minecraft.bukkit.command;

import mz.mzlib.minecraft.MinecraftPlatform;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.util.wrapper.WrapClassForName;
import mz.mzlib.util.wrapper.WrapFieldAccessor;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;
import org.bukkit.command.CommandMap;

@MinecraftPlatform.Enabled(MinecraftPlatform.Tag.PAPER)
@VersionRange(begin = 2102)
@WrapClassForName("io.papermc.paper.plugin.manager.PaperPluginInstanceManager")
public interface PluginInstanceManagerPaperV2102 extends WrapperObject
{
    WrapperFactory<PluginInstanceManagerPaperV2102> FACTORY = WrapperFactory.of(PluginInstanceManagerPaperV2102.class);
    @WrapFieldAccessor("commandMap")
    CommandMap getCommandMap();
}


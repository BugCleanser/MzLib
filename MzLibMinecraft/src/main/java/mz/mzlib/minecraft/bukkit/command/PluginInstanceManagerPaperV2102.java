package mz.mzlib.minecraft.bukkit.command;

import mz.mzlib.minecraft.MinecraftPlatform;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.util.wrapper.*;
import org.bukkit.command.CommandMap;

@MinecraftPlatform.Enabled(MinecraftPlatform.Tag.PAPER)
@VersionRange(begin = 2102)
@WrapClassForName("io.papermc.paper.plugin.manager.PaperPluginInstanceManager")
public interface PluginInstanceManagerPaperV2102 extends WrapperObject
{
    WrapperFactory<PluginInstanceManagerPaperV2102> FACTORY = WrapperFactory.of(PluginInstanceManagerPaperV2102.class);
    @Deprecated
    @WrapperCreator
    static PluginInstanceManagerPaperV2102 create(Object wrapped)
    {
        return WrapperObject.create(PluginInstanceManagerPaperV2102.class, wrapped);
    }

    @WrapFieldAccessor("commandMap")
    CommandMap getCommandMap();
}

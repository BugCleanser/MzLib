package mz.mzlib.minecraft.bukkit.command;

import mz.mzlib.minecraft.MinecraftPlatform;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.util.wrapper.WrapClassForName;
import mz.mzlib.util.wrapper.WrapFieldAccessor;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@MinecraftPlatform.Enabled(MinecraftPlatform.Tag.PAPER)
@VersionRange(begin = 2102)
@WrapClassForName("io.papermc.paper.plugin.manager.PaperPluginManagerImpl")
public interface PluginManagerPaperV2102 extends WrapperObject
{
    WrapperFactory<PluginManagerPaperV2102> FACTORY = WrapperFactory.of(PluginManagerPaperV2102.class);
    @WrapFieldAccessor("instanceManager")
    PluginInstanceManagerPaperV2102 getInstanceManager();
}


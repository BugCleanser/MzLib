package mz.mzlib.minecraft.bukkit.command;

import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.bukkit.PaperEnabled;
import mz.mzlib.util.wrapper.*;

@PaperEnabled
@VersionRange(begin=2102)
@WrapClassForName("io.papermc.paper.plugin.manager.PaperPluginManagerImpl")
public interface PluginManagerPaperV2102 extends WrapperObject
{
    WrapperFactory<PluginManagerPaperV2102> FACTORY = WrapperFactory.of(PluginManagerPaperV2102.class);
    @Deprecated
    @WrapperCreator
    static PluginManagerPaperV2102 create(Object wrapped)
    {
        return WrapperObject.create(PluginManagerPaperV2102.class, wrapped);
    }
    
    @WrapFieldAccessor("instanceManager")
    PluginInstanceManagerPaperV2102 getInstanceManager();
}

package mz.mzlib.minecraft.bukkit.command;

import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.bukkit.PaperOnly;
import mz.mzlib.util.wrapper.WrapClassForName;
import mz.mzlib.util.wrapper.WrapFieldAccessor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@PaperOnly
@VersionRange(begin=2102)
@WrapClassForName("io.papermc.paper.plugin.manager.PaperPluginManagerImpl")
public interface PluginManagerPaperV2102 extends WrapperObject
{
    @WrapperCreator
    static PluginManagerPaperV2102 create(Object wrapped)
    {
        return WrapperObject.create(PluginManagerPaperV2102.class, wrapped);
    }
    
    @WrapFieldAccessor("instanceManager")
    PluginInstanceManagerPaperV2102 getInstanceManager();
}

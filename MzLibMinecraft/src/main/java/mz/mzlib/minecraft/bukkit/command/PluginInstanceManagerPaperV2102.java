package mz.mzlib.minecraft.bukkit.command;

import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.bukkit.PaperEnabled;
import mz.mzlib.util.wrapper.WrapClassForName;
import mz.mzlib.util.wrapper.WrapFieldAccessor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;
import org.bukkit.command.CommandMap;

@PaperEnabled
@VersionRange(begin=2102)
@WrapClassForName("io.papermc.paper.plugin.manager.PaperPluginInstanceManager")
public interface PluginInstanceManagerPaperV2102 extends WrapperObject
{
    @WrapperCreator
    static PluginInstanceManagerPaperV2102 create(Object wrapped)
    {
        return WrapperObject.create(PluginInstanceManagerPaperV2102.class, wrapped);
    }
    
    @WrapFieldAccessor("commandMap")
    CommandMap getCommandMap();
}

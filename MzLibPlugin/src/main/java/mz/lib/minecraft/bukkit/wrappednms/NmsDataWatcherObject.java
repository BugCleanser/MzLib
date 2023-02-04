package mz.lib.minecraft.bukkit.wrappednms;

import mz.lib.minecraft.bukkit.VersionName;
import mz.lib.minecraft.bukkit.wrapper.*;
import mz.lib.wrapper.WrappedObject;

@WrappedBukkitClass({@VersionName(value="nms.DataWatcherObject",maxVer=17),@VersionName(value="net.minecraft.network.syncher.DataWatcherObject",minVer=17)})
public interface NmsDataWatcherObject extends WrappedBukkitObject
{
}

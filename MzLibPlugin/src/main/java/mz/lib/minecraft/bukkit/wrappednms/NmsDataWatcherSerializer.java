package mz.lib.minecraft.bukkit.wrappednms;

import mz.lib.minecraft.bukkit.VersionName;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitClass;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitMethod;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitObject;

@WrappedBukkitClass({@VersionName(value="nms.DataWatcherSerializer",maxVer=17),@VersionName(value="net.minecraft.network.syncher.DataWatcherSerializer",minVer=17)})
public interface NmsDataWatcherSerializer extends WrappedBukkitObject
{
	@WrappedBukkitMethod(@VersionName("@0"))
	NmsDataWatcherObject toDataWatcherObject(int id);
}

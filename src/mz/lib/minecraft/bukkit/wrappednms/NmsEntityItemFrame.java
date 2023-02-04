package mz.lib.minecraft.bukkit.wrappednms;

import mz.lib.minecraft.bukkit.VersionName;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitClass;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitFieldAccessor;
import mz.lib.wrapper.WrappedObject;

@WrappedBukkitClass({@VersionName(value="nms.EntityItemFrame",maxVer=17),@VersionName(value="net.minecraft.world.entity.decoration.EntityItemFrame",minVer=17)})
public interface NmsEntityItemFrame extends NmsEntity
{
	static NmsDataWatcherObject getItemType()
	{
		return WrappedObject.getStatic(NmsEntityItemFrame.class).staticGetItemType();
	}
	@WrappedBukkitFieldAccessor(@VersionName("#0"))
	NmsDataWatcherObject staticGetItemType();
}

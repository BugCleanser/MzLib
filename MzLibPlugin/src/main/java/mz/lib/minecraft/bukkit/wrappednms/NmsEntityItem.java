package mz.lib.minecraft.bukkit.wrappednms;

import mz.lib.minecraft.bukkit.VersionName;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitClass;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitFieldAccessor;
import mz.lib.wrapper.WrappedObject;

@WrappedBukkitClass({@VersionName(value="nms.EntityItem",maxVer=17),@VersionName(value="net.minecraft.world.entity.item.EntityItem",minVer=17)})
public interface NmsEntityItem extends NmsEntity
{
	static NmsDataWatcherObject getItemStackType()
	{
		return WrappedObject.getStatic(NmsEntityItem.class).staticGetItemStackType();
	}
	@WrappedBukkitFieldAccessor(@VersionName({"ITEM","#0"}))
	NmsDataWatcherObject staticGetItemStackType();
}

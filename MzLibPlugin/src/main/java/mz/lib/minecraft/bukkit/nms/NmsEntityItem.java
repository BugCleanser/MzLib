package mz.lib.minecraft.bukkit.nms;

import mz.lib.minecraft.VersionalName;
import mz.lib.minecraft.wrapper.VersionalWrappedClass;
import mz.lib.minecraft.wrapper.VersionalWrappedFieldAccessor;
import mz.lib.wrapper.WrappedObject;

@VersionalWrappedClass({@VersionalName(value="nms.EntityItem",maxVer=17),@VersionalName(value="net.minecraft.world.entity.item.EntityItem",minVer=17)})
public interface NmsEntityItem extends NmsEntity
{
	static NmsDataWatcherObject getItemStackType()
	{
		return WrappedObject.getStatic(NmsEntityItem.class).staticGetItemStackType();
	}
	@VersionalWrappedFieldAccessor(@VersionalName({"ITEM","#0"}))
	NmsDataWatcherObject staticGetItemStackType();
}

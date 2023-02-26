package mz.lib.minecraft.bukkit.nms;

import mz.lib.minecraft.VersionalName;
import mz.lib.minecraft.wrapper.VersionalWrappedClass;
import mz.lib.minecraft.wrapper.VersionalWrappedFieldAccessor;
import mz.lib.wrapper.WrappedObject;

@VersionalWrappedClass({@VersionalName(value="nms.EntityItemFrame",maxVer=17),@VersionalName(value="net.minecraft.world.entity.decoration.EntityItemFrame",minVer=17)})
public interface NmsEntityItemFrame extends NmsEntity
{
	static NmsDataWatcherObject getItemType()
	{
		return WrappedObject.getStatic(NmsEntityItemFrame.class).staticGetItemType();
	}
	@VersionalWrappedFieldAccessor(@VersionalName("#0"))
	NmsDataWatcherObject staticGetItemType();
}

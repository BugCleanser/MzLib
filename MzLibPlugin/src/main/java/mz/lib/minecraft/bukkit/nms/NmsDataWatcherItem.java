package mz.lib.minecraft.bukkit.nms;

import mz.lib.minecraft.VersionalName;
import mz.lib.minecraft.wrapper.*;
import mz.lib.wrapper.WrappedConstructor;
import mz.lib.wrapper.WrappedMethod;
import mz.lib.wrapper.WrappedObject;
import mz.lib.wrapper.*;

@VersionalWrappedClass({@VersionalName(value="nms.DataWatcher$Item",maxVer=17),@VersionalName(value="net.minecraft.network.syncher.DataWatcher$Item",minVer=17)})
public interface NmsDataWatcherItem extends VersionalWrappedObject
{
	static NmsDataWatcherItem newInstance(NmsDataWatcherObject type,Object value)
	{
		return WrappedObject.getStatic(NmsDataWatcherItem.class).staticNewInstance(type,value);
	}
	@WrappedMethod("a")
	NmsDataWatcherObject getType();
	@WrappedMethod("b")
	Object getValue();
	default <R extends WrappedObject> R getValue(Class<R> wrapper)
	{
		return WrappedObject.wrap(wrapper,getValue());
	}
	@WrappedConstructor
	NmsDataWatcherItem staticNewInstance(NmsDataWatcherObject type,Object value);
}

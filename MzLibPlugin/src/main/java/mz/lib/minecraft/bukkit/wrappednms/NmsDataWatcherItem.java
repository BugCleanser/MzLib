package mz.lib.minecraft.bukkit.wrappednms;

import mz.lib.minecraft.bukkit.VersionName;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitClass;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitObject;
import mz.lib.wrapper.WrappedConstructor;
import mz.lib.wrapper.WrappedMethod;
import mz.lib.wrapper.WrappedObject;

@WrappedBukkitClass({@VersionName(value="nms.DataWatcher$Item",maxVer=17),@VersionName(value="net.minecraft.network.syncher.DataWatcher$Item",minVer=17)})
public interface NmsDataWatcherItem extends WrappedBukkitObject
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

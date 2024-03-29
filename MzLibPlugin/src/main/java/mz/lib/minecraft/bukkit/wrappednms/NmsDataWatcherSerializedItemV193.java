package mz.lib.minecraft.bukkit.wrappednms;

import mz.lib.minecraft.bukkit.VersionName;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitClass;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitFieldAccessor;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitMethod;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitObject;
import mz.lib.wrapper.WrappedObject;

@WrappedBukkitClass(@VersionName(minVer=19.3f,value="net.minecraft.network.syncher.DataWatcher$b"))
public interface NmsDataWatcherSerializedItemV193 extends WrappedBukkitObject
{
	@WrappedBukkitFieldAccessor(@VersionName("@0"))
	int getId();
	@WrappedBukkitFieldAccessor(@VersionName("@0"))
	NmsDataWatcherSerializer getSerializer();
	@WrappedBukkitFieldAccessor(@VersionName("@0"))
	Object getValue();
	
	default NmsDataWatcherItem toDataWatcherItem()
	{
		return NmsDataWatcherItem.newInstance(getSerializer().toDataWatcherObject(getId()),getValue());
	}
	
	static NmsDataWatcherSerializedItemV193 newInstance(NmsDataWatcherItem dwi)
	{
		return newInstance(dwi.getType(),dwi.getValue());
	}
	static NmsDataWatcherSerializedItemV193 newInstance(NmsDataWatcherObject dwo,Object value)
	{
		return WrappedObject.getStatic(NmsDataWatcherSerializedItemV193.class).staticNewInstance(dwo,value);
	}
	@WrappedBukkitMethod(@VersionName("#0"))
	NmsDataWatcherSerializedItemV193 staticNewInstance(NmsDataWatcherObject dwo,Object value);
}

package mz.lib.minecraft.bukkit.nms;

import mz.lib.minecraft.*;
import mz.lib.minecraft.wrapper.*;
import mz.lib.wrapper.*;
import mz.mzlib.*;
import mz.mzlib.wrapper.*;

@VersionalWrappedClass(@VersionalName(minVer=19.3f,value="net.minecraft.network.syncher.DataWatcher$b"))
public interface NmsDataWatcherSerializedItemV193 extends VersionalWrappedObject
{
	@VersionalWrappedFieldAccessor(@VersionalName("@0"))
	int getId();
	@VersionalWrappedFieldAccessor(@VersionalName("@0"))
	NmsDataWatcherSerializer getSerializer();
	@VersionalWrappedFieldAccessor(@VersionalName("@0"))
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
	@VersionalWrappedMethod(@VersionalName("#0"))
	NmsDataWatcherSerializedItemV193 staticNewInstance(NmsDataWatcherObject dwo,Object value);
}

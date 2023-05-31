package mz.lib.minecraft.bukkit.nms;

import mz.lib.minecraft.*;
import mz.lib.minecraft.wrapper.*;
import mz.mzlib.*;
import mz.mzlib.wrapper.*;

@VersionalWrappedClass({@VersionalName(value="nms.DataWatcherSerializer",maxVer=17),@VersionalName(value="net.minecraft.network.syncher.DataWatcherSerializer",minVer=17)})
public interface NmsDataWatcherSerializer extends VersionalWrappedObject
{
	@VersionalWrappedMethod(@VersionalName("@0"))
	NmsDataWatcherObject toDataWatcherObject(int id);
}

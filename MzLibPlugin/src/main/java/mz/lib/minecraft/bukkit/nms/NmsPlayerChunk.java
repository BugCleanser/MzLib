package mz.lib.minecraft.bukkit.nms;

import mz.lib.minecraft.*;
import mz.lib.minecraft.wrapper.*;
import mz.lib.*;
import mz.lib.wrapper.*;

@VersionalWrappedClass({@VersionalName(value="nms.PlayerChunk",maxVer=17),@VersionalName(value="net.minecraft.server.level.PlayerChunk",minVer=17)})
public interface NmsPlayerChunk extends VersionalWrappedObject
{
	@VersionalWrappedMethod(@VersionalName("@0"))
	NmsChunk getChunk();
}

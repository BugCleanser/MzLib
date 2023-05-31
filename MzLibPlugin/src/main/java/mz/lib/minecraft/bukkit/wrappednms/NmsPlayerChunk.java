package mz.lib.minecraft.bukkit.wrappednms;

import mz.lib.minecraft.bukkit.*;
import mz.lib.minecraft.bukkit.wrapper.*;
@WrappedBukkitClass({@VersionName(value="nms.PlayerChunk",maxVer=17),@VersionName(value="net.minecraft.server.level.PlayerChunk",minVer=17)})
public interface NmsPlayerChunk extends WrappedBukkitObject
{
	@WrappedBukkitMethod(@VersionName("@0"))
	NmsChunk getChunk();
}

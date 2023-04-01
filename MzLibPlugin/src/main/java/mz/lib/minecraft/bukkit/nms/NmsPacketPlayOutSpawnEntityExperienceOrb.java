package mz.lib.minecraft.bukkit.nms;

import mz.lib.minecraft.*;
import mz.lib.minecraft.wrapper.*;
import mz.lib.*;
import mz.lib.wrapper.*;

@VersionalWrappedClass({@VersionalName(value="nms.PacketPlayOutSpawnEntityExperienceOrb",maxVer=17),@VersionalName(value="net.minecraft.network.protocol.game.PacketPlayOutSpawnEntityExperienceOrb",minVer=17)})
public interface NmsPacketPlayOutSpawnEntityExperienceOrb extends NmsPacket
{
	@VersionalWrappedFieldAccessor(@VersionalName("@0"))
	int getEntityId();
	@VersionalWrappedFieldAccessor(@VersionalName("@0"))
	NmsPacketPlayOutSpawnEntityExperienceOrb setEntityId(int id);
}

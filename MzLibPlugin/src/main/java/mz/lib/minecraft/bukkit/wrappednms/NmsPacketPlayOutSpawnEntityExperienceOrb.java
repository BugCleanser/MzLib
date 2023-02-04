package mz.lib.minecraft.bukkit.wrappednms;

import mz.lib.minecraft.bukkit.*;
import mz.lib.minecraft.bukkit.wrapper.*;
import org.bukkit.entity.*;

import java.util.*;

@WrappedBukkitClass({@VersionName(value="nms.PacketPlayOutSpawnEntityExperienceOrb",maxVer=17),@VersionName(value="net.minecraft.network.protocol.game.PacketPlayOutSpawnEntityExperienceOrb",minVer=17)})
public interface NmsPacketPlayOutSpawnEntityExperienceOrb extends NmsPacket
{
	@WrappedBukkitFieldAccessor(@VersionName("@0"))
	int getEntityId();
	@WrappedBukkitFieldAccessor(@VersionName("@0"))
	NmsPacketPlayOutSpawnEntityExperienceOrb setEntityId(int id);
}

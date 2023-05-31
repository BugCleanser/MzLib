package mz.lib.minecraft.bukkit.wrappednms;

import mz.lib.minecraft.bukkit.*;
import mz.lib.minecraft.bukkit.entity.*;
import mz.lib.minecraft.bukkit.wrapper.*;
import org.bukkit.entity.*;

import java.util.*;

@WrappedBukkitClass({@VersionName(value="nms.PacketPlayOutSpawnEntityLiving",maxVer=17),@VersionName(value="net.minecraft.network.protocol.game.PacketPlayOutSpawnEntityLiving",minVer=17,maxVer=19)})
public interface NmsPacketPlayOutSpawnEntityLivingV_19 extends NmsPacket
{
	@WrappedBukkitFieldAccessor(@VersionName("@0"))
	int getEntityId();
	@WrappedBukkitFieldAccessor(@VersionName("@0"))
	NmsPacketPlayOutSpawnEntityLivingV_19 setEntityId(int id);
	
	@WrappedBukkitFieldAccessor(@VersionName("@0"))
	UUID getUUID();
	@WrappedBukkitFieldAccessor(@VersionName("@0"))
	NmsPacketPlayOutSpawnEntityLivingV_19 setUUID(UUID id);
	
	default EntityType getEntityType()
	{
		return EntityTypeUtil.fromId(getEntityType0());
	}
	@WrappedBukkitFieldAccessor(@VersionName("@1"))
	int getEntityType0();
}

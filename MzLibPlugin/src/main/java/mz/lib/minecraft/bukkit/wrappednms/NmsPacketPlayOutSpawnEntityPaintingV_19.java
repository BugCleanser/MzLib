package mz.lib.minecraft.bukkit.wrappednms;

import mz.lib.minecraft.bukkit.VersionName;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitClass;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitFieldAccessor;

import java.util.UUID;

@WrappedBukkitClass({@VersionName(value="nms.PacketPlayOutSpawnEntityPainting",maxVer=17),@VersionName(value="net.minecraft.network.protocol.game.PacketPlayOutSpawnEntityPainting",minVer=17,maxVer=19)})
public interface NmsPacketPlayOutSpawnEntityPaintingV_19 extends NmsPacket
{
	@WrappedBukkitFieldAccessor(@VersionName("@0"))
	int getEntityId();
	@WrappedBukkitFieldAccessor(@VersionName("@0"))
	NmsPacketPlayOutSpawnEntityPaintingV_19 setEntityId(int id);
	
	@WrappedBukkitFieldAccessor(@VersionName("@0"))
	UUID getUUID();
	@WrappedBukkitFieldAccessor(@VersionName("@0"))
	NmsPacketPlayOutSpawnEntityPaintingV_19 setUUID(UUID id);
}

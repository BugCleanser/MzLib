package mz.lib.minecraft.bukkit.wrappednms;

import mz.lib.minecraft.bukkit.VersionName;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitClass;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitFieldAccessor;

@WrappedBukkitClass({@VersionName(value="nms.PacketPlayInItemName",maxVer=17),@VersionName(value="net.minecraft.network.protocol.game.PacketPlayInItemName",minVer=17)})
public interface NmsPacketPlayInItemNameV13 extends NmsPacket
{
	@WrappedBukkitFieldAccessor(@VersionName("a"))
	String getName();
	@WrappedBukkitFieldAccessor(@VersionName("a"))
	void setName(String name);
}

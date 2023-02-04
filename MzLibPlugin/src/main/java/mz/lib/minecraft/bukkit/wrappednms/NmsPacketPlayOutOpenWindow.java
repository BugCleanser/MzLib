package mz.lib.minecraft.bukkit.wrappednms;

import mz.lib.minecraft.bukkit.VersionName;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitClass;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitFieldAccessor;

@WrappedBukkitClass({@VersionName(value="nms.PacketPlayOutOpenWindow",maxVer=17),@VersionName(value="net.minecraft.network.protocol.game.PacketPlayOutOpenWindow",minVer=17)})
public interface NmsPacketPlayOutOpenWindow extends NmsPacket
{
	@WrappedBukkitFieldAccessor(@VersionName("c"))
	NmsIChatBaseComponent getTitle();
	@WrappedBukkitFieldAccessor(@VersionName("c"))
	NmsPacketPlayOutOpenWindow setTitle(NmsIChatBaseComponent title);
}

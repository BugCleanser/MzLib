package mz.lib.minecraft.bukkit.wrappednms;

import mz.lib.minecraft.bukkit.VersionName;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitClass;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitFieldAccessor;

@WrappedBukkitClass({@VersionName(value="nms.PlayerConnection",maxVer=17),@VersionName(value="net.minecraft.server.network.PlayerConnection",minVer=17)})
public interface NmsPlayerConnection extends NmsPacketListener
{
	@WrappedBukkitFieldAccessor({@VersionName("networkManager"),@VersionName(minVer=17, value="a",maxVer=19),@VersionName(value="b",minVer=19)})
	NmsNetworkManager getNetworkManager();
	
	@WrappedBukkitFieldAccessor({@VersionName("player"),@VersionName(minVer=17, value="b",maxVer=19),@VersionName(value="c",minVer=19)})
	NmsEntityPlayer getPlayer();
}

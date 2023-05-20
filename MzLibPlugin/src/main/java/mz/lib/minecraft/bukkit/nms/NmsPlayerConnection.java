package mz.lib.minecraft.bukkit.nms;

import mz.lib.minecraft.VersionalName;
import mz.lib.minecraft.wrapper.VersionalWrappedClass;
import mz.lib.minecraft.wrapper.VersionalWrappedFieldAccessor;

@VersionalWrappedClass({@VersionalName(value="nms.PlayerConnection",maxVer=17),@VersionalName(value="net.minecraft.server.network.PlayerConnection",minVer=17)})
public interface NmsPlayerConnection extends NmsPacketListener
{
	@VersionalWrappedFieldAccessor({@VersionalName("networkManager"),@VersionalName(minVer=17, value="a",maxVer=19),@VersionalName(value="b",minVer=19)})
	NmsNetworkManager getNetworkManager();
	
	@WrappedBukkitFieldAccessor({@VersionName("player"),@VersionName(minVer=17, value="b",maxVer=19),@VersionName(value="c",minVer=19,maxVer=19.4f),@VersionName(value="@0",minVer=19.4f)})
	NmsEntityPlayer getPlayer();
}

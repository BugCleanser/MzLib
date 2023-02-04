package mz.lib.minecraft.bukkit.wrappednms;

import mz.lib.minecraft.bukkit.VersionName;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitClass;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitFieldAccessor;

@WrappedBukkitClass({@VersionName(value="nms.PacketPlayInChat",maxVer=17),@VersionName(value="net.minecraft.network.protocol.game.PacketPlayInChat",minVer=17)})
public interface NmsPacketPlayInChat extends NmsPacket
{
	@WrappedBukkitFieldAccessor({@VersionName(value="a", maxVer=17),@VersionName(value="b", minVer=17,maxVer=19.1f),@VersionName(value="a",minVer=19.1f)})
	String getMsg();
	@WrappedBukkitFieldAccessor({@VersionName(value="a", maxVer=17),@VersionName(value="b", minVer=17,maxVer=19.1f),@VersionName(value="a",minVer=19.1f)})
	void setMsg(String msg);
}

package mz.lib.minecraft.bukkit.nms;

import mz.lib.minecraft.VersionalName;
import mz.lib.minecraft.wrapper.VersionalWrappedClass;
import mz.lib.minecraft.wrapper.VersionalWrappedFieldAccessor;

@VersionalWrappedClass({@VersionalName(value="nms.PacketPlayInChat",maxVer=17),@VersionalName(value="net.minecraft.network.protocol.game.PacketPlayInChat",minVer=17)})
public interface NmsPacketPlayInChat extends NmsPacket
{
	@VersionalWrappedFieldAccessor({@VersionalName(value="a", maxVer=17),@VersionalName(value="b", minVer=17,maxVer=19.1f),@VersionalName(value="a",minVer=19.1f)})
	String getMsg();
	@VersionalWrappedFieldAccessor({@VersionalName(value="a", maxVer=17),@VersionalName(value="b", minVer=17,maxVer=19.1f),@VersionalName(value="a",minVer=19.1f)})
	void setMsg(String msg);
}

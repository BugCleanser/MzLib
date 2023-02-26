package mz.lib.minecraft.bukkit.nms;

import mz.lib.minecraft.VersionalName;
import mz.lib.minecraft.wrapper.VersionalWrappedClass;
import mz.lib.minecraft.wrapper.VersionalWrappedFieldAccessor;

@VersionalWrappedClass({@VersionalName(value="nms.PacketPlayInItemName",maxVer=17),@VersionalName(value="net.minecraft.network.protocol.game.PacketPlayInItemName",minVer=17)})
public interface NmsPacketPlayInItemNameV13 extends NmsPacket
{
	@VersionalWrappedFieldAccessor(@VersionalName("a"))
	String getName();
	@VersionalWrappedFieldAccessor(@VersionalName("a"))
	void setName(String name);
}

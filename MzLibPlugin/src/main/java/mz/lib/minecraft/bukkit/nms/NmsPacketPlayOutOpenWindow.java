package mz.lib.minecraft.bukkit.nms;

import mz.lib.minecraft.VersionalName;
import mz.lib.minecraft.wrapper.VersionalWrappedClass;
import mz.lib.minecraft.wrapper.VersionalWrappedFieldAccessor;

@VersionalWrappedClass({@VersionalName(value="nms.PacketPlayOutOpenWindow",maxVer=17),@VersionalName(value="net.minecraft.network.protocol.game.PacketPlayOutOpenWindow",minVer=17)})
public interface NmsPacketPlayOutOpenWindow extends NmsPacket
{
	@VersionalWrappedFieldAccessor(@VersionalName("c"))
	NmsIChatBaseComponent getTitle();
	@VersionalWrappedFieldAccessor(@VersionalName("c"))
	NmsPacketPlayOutOpenWindow setTitle(NmsIChatBaseComponent title);
}

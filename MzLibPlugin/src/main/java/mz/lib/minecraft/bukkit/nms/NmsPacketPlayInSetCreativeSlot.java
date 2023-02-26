package mz.lib.minecraft.bukkit.nms;

import mz.lib.minecraft.VersionalName;
import mz.lib.minecraft.wrapper.VersionalWrappedClass;
import mz.lib.minecraft.wrapper.VersionalWrappedFieldAccessor;

@VersionalWrappedClass({@VersionalName(value="nms.PacketPlayInSetCreativeSlot",maxVer=17),@VersionalName(value="net.minecraft.network.protocol.game.PacketPlayInSetCreativeSlot",minVer=17)})
public interface NmsPacketPlayInSetCreativeSlot extends NmsPacket
{
	@VersionalWrappedFieldAccessor({@VersionalName("slot"),@VersionalName(minVer=17, value="a")})
	int getSlot();
	@VersionalWrappedFieldAccessor({@VersionalName("slot"),@VersionalName(minVer=17, value="a")})
	NmsPacketPlayInSetCreativeSlot setSlot(int slot);
	
	@VersionalWrappedFieldAccessor(@VersionalName("b"))
	NmsItemStack getItem();
	@VersionalWrappedFieldAccessor(@VersionalName("b"))
	NmsPacketPlayInSetCreativeSlot setItem(NmsItemStack item);
}

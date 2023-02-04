package mz.lib.minecraft.bukkit.wrappednms;

import mz.lib.minecraft.bukkit.VersionName;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitClass;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitFieldAccessor;

@WrappedBukkitClass({@VersionName(value="nms.PacketPlayInSetCreativeSlot",maxVer=17),@VersionName(value="net.minecraft.network.protocol.game.PacketPlayInSetCreativeSlot",minVer=17)})
public interface NmsPacketPlayInSetCreativeSlot extends NmsPacket
{
	@WrappedBukkitFieldAccessor({@VersionName("slot"),@VersionName(minVer=17, value="a")})
	int getSlot();
	@WrappedBukkitFieldAccessor({@VersionName("slot"),@VersionName(minVer=17, value="a")})
	NmsPacketPlayInSetCreativeSlot setSlot(int slot);
	
	@WrappedBukkitFieldAccessor(@VersionName("b"))
	NmsItemStack getItem();
	@WrappedBukkitFieldAccessor(@VersionName("b"))
	NmsPacketPlayInSetCreativeSlot setItem(NmsItemStack item);
}

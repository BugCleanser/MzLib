package mz.lib.minecraft.bukkit.wrappednms;

import mz.lib.minecraft.bukkit.VersionName;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitClass;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitFieldAccessor;

@WrappedBukkitClass({@VersionName(value="nms.PacketPlayInAutoRecipe",maxVer=17),@VersionName(value="net.minecraft.network.protocol.game.PacketPlayInAutoRecipe",minVer=17)})
public interface NmsPacketPlayInAutoRecipe extends NmsPacket
{
	@WrappedBukkitFieldAccessor(@VersionName("a"))
	int getWindowId();
	@WrappedBukkitFieldAccessor(@VersionName("a"))
	NmsPacketPlayInAutoRecipe setWindowId(int id);
	
	@WrappedBukkitFieldAccessor(@VersionName(value="b",minVer=12.2f,maxVer=13))
	NmsIRecipe getRecipeV122_13();
	@WrappedBukkitFieldAccessor(@VersionName(value="b",minVer=12.2f,maxVer=13))
	NmsPacketPlayInAutoRecipe setRecipeV122_13(NmsIRecipe recipe);
	
	@WrappedBukkitFieldAccessor(@VersionName(value="b",minVer=13))
	NmsMinecraftKey getKeyV13();
	@WrappedBukkitFieldAccessor(@VersionName(value="b",minVer=13))
	NmsPacketPlayInAutoRecipe setKeyV13(NmsMinecraftKey key);
	
	@WrappedBukkitFieldAccessor(@VersionName(value="c",minVer=12.2f))
	boolean isCraftAllV122();
	@WrappedBukkitFieldAccessor(@VersionName(value="c",minVer=12.2f))
	NmsPacketPlayInAutoRecipe setCraftAllV122(boolean craftAll);
}

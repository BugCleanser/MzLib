package mz.lib.minecraft.bukkit.nms;

import mz.lib.minecraft.VersionalName;
import mz.lib.minecraft.wrapper.VersionalWrappedClass;
import mz.lib.minecraft.wrapper.VersionalWrappedFieldAccessor;

@VersionalWrappedClass({@VersionalName(value="nms.PacketPlayInAutoRecipe",maxVer=17),@VersionalName(value="net.minecraft.network.protocol.game.PacketPlayInAutoRecipe",minVer=17)})
public interface NmsPacketPlayInAutoRecipe extends NmsPacket
{
	@VersionalWrappedFieldAccessor(@VersionalName("a"))
	int getWindowId();
	@VersionalWrappedFieldAccessor(@VersionalName("a"))
	NmsPacketPlayInAutoRecipe setWindowId(int id);
	
	@VersionalWrappedFieldAccessor(@VersionalName(value="b",minVer=12.2f,maxVer=13))
	NmsIRecipe getRecipeV122_13();
	@VersionalWrappedFieldAccessor(@VersionalName(value="b",minVer=12.2f,maxVer=13))
	NmsPacketPlayInAutoRecipe setRecipeV122_13(NmsIRecipe recipe);
	
	@VersionalWrappedFieldAccessor(@VersionalName(value="b",minVer=13))
	NmsMinecraftKey getKeyV13();
	@VersionalWrappedFieldAccessor(@VersionalName(value="b",minVer=13))
	NmsPacketPlayInAutoRecipe setKeyV13(NmsMinecraftKey key);
	
	@VersionalWrappedFieldAccessor(@VersionalName(value="c",minVer=12.2f))
	boolean isCraftAllV122();
	@VersionalWrappedFieldAccessor(@VersionalName(value="c",minVer=12.2f))
	NmsPacketPlayInAutoRecipe setCraftAllV122(boolean craftAll);
}

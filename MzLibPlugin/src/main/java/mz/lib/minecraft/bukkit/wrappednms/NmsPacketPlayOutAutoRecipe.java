package mz.lib.minecraft.bukkit.wrappednms;

import mz.lib.minecraft.bukkit.VersionName;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitClass;
import mz.lib.wrapper.WrappedConstructor;
import mz.lib.wrapper.WrappedObject;

@WrappedBukkitClass({@VersionName(value="nms.PacketPlayOutAutoRecipe",maxVer=17),@VersionName(value="net.minecraft.network.protocol.game.PacketPlayOutAutoRecipe",minVer=17)})
public interface NmsPacketPlayOutAutoRecipe extends NmsPacket
{
	@WrappedConstructor
	NmsPacketPlayOutAutoRecipe staticNewInstance(int windowId,NmsIRecipe recipe);
	static NmsPacketPlayOutAutoRecipe newInstance(int windowId,NmsIRecipe recipe)
	{
		return WrappedObject.getStatic(NmsPacketPlayOutAutoRecipe.class).staticNewInstance(windowId,recipe);
	}
}

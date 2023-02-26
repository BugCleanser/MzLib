package mz.lib.minecraft.bukkit.nms;

import mz.lib.wrapper.*;
import mz.lib.minecraft.VersionalName;
import mz.lib.minecraft.wrapper.VersionalWrappedClass;

@VersionalWrappedClass({@VersionalName(value="nms.PacketPlayOutAutoRecipe",maxVer=17),@VersionalName(value="net.minecraft.network.protocol.game.PacketPlayOutAutoRecipe",minVer=17)})
public interface NmsPacketPlayOutAutoRecipe extends NmsPacket
{
	@WrappedConstructor
	NmsPacketPlayOutAutoRecipe staticNewInstance(int windowId,NmsIRecipe recipe);
	static NmsPacketPlayOutAutoRecipe newInstance(int windowId,NmsIRecipe recipe)
	{
		return getStatic(NmsPacketPlayOutAutoRecipe.class).staticNewInstance(windowId,recipe);
	}
}

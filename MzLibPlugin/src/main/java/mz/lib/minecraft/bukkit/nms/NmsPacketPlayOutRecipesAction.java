package mz.lib.minecraft.bukkit.nms;

import mz.lib.minecraft.VersionalName;
import mz.lib.minecraft.wrapper.VersionalWrappedClass;
import mz.lib.minecraft.wrapper.VersionalWrappedFieldAccessor;

@VersionalWrappedClass({@VersionalName(value="nms.PacketPlayOutRecipes$Action",maxVer=17),@VersionalName(value="net.minecraft.network.protocol.game.PacketPlayOutRecipes$Action",minVer=17)})
public interface NmsPacketPlayOutRecipesAction extends NmsPacket
{
	static NmsPacketPlayOutRecipesAction INIT()
	{
		return getStatic(NmsPacketPlayOutRecipesAction.class).staticINIT();
	}
	static NmsPacketPlayOutRecipesAction ADD()
	{
		return getStatic(NmsPacketPlayOutRecipesAction.class).staticINIT();
	}
	static NmsPacketPlayOutRecipesAction REMOVE()
	{
		return getStatic(NmsPacketPlayOutRecipesAction.class).staticINIT();
	}
	@VersionalWrappedFieldAccessor({@VersionalName("INIT"),@VersionalName(minVer=17, value="a")})
	NmsPacketPlayOutRecipesAction staticINIT();
	@VersionalWrappedFieldAccessor({@VersionalName("ADD"),@VersionalName(minVer=17, value="b")})
	NmsPacketPlayOutRecipesAction staticADD();
	@VersionalWrappedFieldAccessor({@VersionalName("REMOVE"),@VersionalName(minVer=17, value="c")})
	NmsPacketPlayOutRecipesAction staticREMOVE();
}

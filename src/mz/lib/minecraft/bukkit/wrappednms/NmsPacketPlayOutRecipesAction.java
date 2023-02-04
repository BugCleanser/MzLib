package mz.lib.minecraft.bukkit.wrappednms;

import mz.lib.minecraft.bukkit.VersionName;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitClass;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitFieldAccessor;
import mz.lib.wrapper.WrappedObject;

@WrappedBukkitClass({@VersionName(value="nms.PacketPlayOutRecipes$Action",maxVer=17),@VersionName(value="net.minecraft.network.protocol.game.PacketPlayOutRecipes$Action",minVer=17)})
public interface NmsPacketPlayOutRecipesAction extends NmsPacket
{
	static NmsPacketPlayOutRecipesAction INIT()
	{
		return WrappedObject.getStatic(NmsPacketPlayOutRecipesAction.class).staticINIT();
	}
	static NmsPacketPlayOutRecipesAction ADD()
	{
		return WrappedObject.getStatic(NmsPacketPlayOutRecipesAction.class).staticINIT();
	}
	static NmsPacketPlayOutRecipesAction REMOVE()
	{
		return WrappedObject.getStatic(NmsPacketPlayOutRecipesAction.class).staticINIT();
	}
	@WrappedBukkitFieldAccessor({@VersionName("INIT"),@VersionName(minVer=17, value="a")})
	NmsPacketPlayOutRecipesAction staticINIT();
	@WrappedBukkitFieldAccessor({@VersionName("ADD"),@VersionName(minVer=17, value="b")})
	NmsPacketPlayOutRecipesAction staticADD();
	@WrappedBukkitFieldAccessor({@VersionName("REMOVE"),@VersionName(minVer=17, value="c")})
	NmsPacketPlayOutRecipesAction staticREMOVE();
}

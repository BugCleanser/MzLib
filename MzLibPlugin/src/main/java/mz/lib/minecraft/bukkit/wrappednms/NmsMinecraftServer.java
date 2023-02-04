package mz.lib.minecraft.bukkit.wrappednms;

import mz.lib.minecraft.bukkit.VersionName;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitClass;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitMethod;
import mz.lib.wrapper.WrappedMethod;
import mz.lib.wrapper.WrappedObject;

@WrappedBukkitClass({@VersionName(value="nms.MinecraftServer",maxVer=17),@VersionName(value="net.minecraft.server.MinecraftServer",minVer=17)})
public interface NmsMinecraftServer extends NmsICommandListener
{
	static NmsMinecraftServer getServer()
	{
		return WrappedObject.getStatic(NmsMinecraftServer.class).staticGetServer();
	}
	@WrappedMethod("getServer")
	NmsMinecraftServer staticGetServer();
	@WrappedBukkitMethod(@VersionName(minVer=13,value={"getCraftingManager","@0"}))
	NmsCraftingManager getCraftingManagerV13();
}

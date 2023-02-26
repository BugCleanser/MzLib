package mz.lib.minecraft.bukkit.nms;

import mz.lib.minecraft.VersionalName;
import mz.lib.minecraft.wrapper.VersionalWrappedClass;
import mz.lib.minecraft.wrapper.VersionalWrappedMethod;
import mz.lib.wrapper.WrappedMethod;
import mz.lib.wrapper.WrappedObject;

@VersionalWrappedClass({@VersionalName(value="nms.MinecraftServer",maxVer=17),@VersionalName(value="net.minecraft.server.MinecraftServer",minVer=17)})
public interface NmsMinecraftServer extends NmsICommandListener
{
	static NmsMinecraftServer getServer()
	{
		return WrappedObject.getStatic(NmsMinecraftServer.class).staticGetServer();
	}
	@WrappedMethod("getServer")
	NmsMinecraftServer staticGetServer();
	@VersionalWrappedMethod(@VersionalName(minVer=13,value={"getCraftingManager","@0"}))
	NmsCraftingManager getCraftingManagerV13();
}

package mz.lib.minecraft.bukkit.wrappednms;

import mz.lib.minecraft.bukkit.VersionName;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitClass;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitObject;
import mz.lib.wrapper.WrappedMethod;
import mz.lib.wrapper.WrappedObject;

@WrappedBukkitClass({@VersionName(value="nms.IChatBaseComponent",maxVer=17),@VersionName(value="net.minecraft.network.chat.IChatBaseComponent",minVer=17)})
public interface NmsIChatBaseComponent extends WrappedBukkitObject
{
	@WrappedBukkitClass({@VersionName(value="nms.IChatBaseComponent$ChatSerializer",maxVer=17),@VersionName(value="net.minecraft.network.chat.IChatBaseComponent$ChatSerializer",minVer=17)})
	interface NmsChatSerializer extends WrappedBukkitObject
	{
		static NmsIChatBaseComponent jsonToComponent(String json)
		{
			return WrappedObject.getStatic(NmsChatSerializer.class).staticJsonToComponent(json);
		}
		static String toJson(NmsIChatBaseComponent s)
		{
			return WrappedObject.getStatic(NmsChatSerializer.class).staticToJson(s);
		}
		@WrappedMethod({"jsonToComponent","a"})
		NmsIChatBaseComponent staticJsonToComponent(String json);
		@WrappedMethod({"a"})
		String staticToJson(NmsIChatBaseComponent s);
	}
}

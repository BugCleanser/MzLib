package mz.lib.minecraft.bukkit.nms;

import mz.lib.minecraft.message.*;
import mz.lib.minecraft.wrapper.*;
import mz.lib.minecraft.VersionalName;
import mz.lib.wrapper.WrappedMethod;
import mz.lib.wrapper.WrappedObject;

@VersionalWrappedClass({@VersionalName(value="nms.IChatBaseComponent",maxVer=17),@VersionalName(value="net.minecraft.network.chat.IChatBaseComponent",minVer=17)})
public interface NmsIChatBaseComponent extends VersionalWrappedObject, MessageComponent
{
	@VersionalWrappedMethod({@VersionalName("@0")})
	NmsChatModifier getStyle();
	
	default String toJson()
	{
		return NmsChatSerializer.toJson(this);
	}
	
	
	@VersionalWrappedClass({@VersionalName(value="nms.IChatBaseComponent$ChatSerializer",maxVer=17),@VersionalName(value="net.minecraft.network.chat.IChatBaseComponent$ChatSerializer",minVer=17)})
	interface NmsChatSerializer extends VersionalWrappedObject
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

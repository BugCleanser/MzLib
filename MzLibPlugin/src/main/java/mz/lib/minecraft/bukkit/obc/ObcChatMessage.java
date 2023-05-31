package mz.lib.minecraft.bukkit.obc;

import mz.lib.minecraft.*;
import mz.lib.minecraft.wrapper.*;
import mz.mzlib.*;
import mz.lib.minecraft.bukkit.nms.NmsIChatBaseComponent;
import mz.lib.minecraft.bukkit.nms.NmsIChatBaseComponent.NmsChatSerializer;
import mz.lib.wrapper.WrappedObject;
import mz.mzlib.wrapper.*;

@VersionalWrappedClass(@VersionalName("obc.util.CraftChatMessage"))
public interface ObcChatMessage extends VersionalWrappedObject
{
	static String fromComponentV13(NmsIChatBaseComponent cc)
	{
		return WrappedObject.getStatic(ObcChatMessage.class).staticFromComponentV13(cc);
	}
	static String fromStringOrNullToJSONV17(String s)
	{
		return WrappedObject.getStatic(ObcChatMessage.class).staticFromStringOrNullToJSONV17(s);
	}
	static NmsIChatBaseComponent fromStringOrNullV13(String s)
	{
		return WrappedObject.getStatic(ObcChatMessage.class).staticFromStringOrNullV13(s);
	}
	static String fromStringOrNullToJSONV13(String s)
	{
		if(Server.instance.v17)
			return fromStringOrNullToJSONV17(s);
		else
			return toJson(fromStringOrNullV13(s));
	}
	static String fromJSONComponentV17(String s)
	{
		return WrappedObject.getStatic(ObcChatMessage.class).staticFromJSONComponentV17(s);
	}
	static String fromJSONComponentV13(String json)
	{
		if(Server.instance.v17)
			return fromJSONComponentV17(json);
		else
			return fromComponentV13(NmsChatSerializer.jsonToComponent(json));
	}
	static String toJson(NmsIChatBaseComponent s)
	{
		return NmsChatSerializer.toJson(s);
	}
	@VersionalWrappedMethod(@VersionalName(value="fromComponent",minVer=13))
	String staticFromComponentV13(NmsIChatBaseComponent cc);
	@VersionalWrappedMethod(@VersionalName(value="fromStringOrNullToJSON",minVer=17))
	String staticFromStringOrNullToJSONV17(String s);
	@VersionalWrappedMethod(@VersionalName(value="fromStringOrNull",minVer=13))
	NmsIChatBaseComponent staticFromStringOrNullV13(String s);
	@VersionalWrappedMethod(@VersionalName(value="fromJSONComponent",minVer=17))
	String staticFromJSONComponentV17(String s);
}

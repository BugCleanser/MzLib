package mz.lib.minecraft.bukkit.wrappedobc;

import mz.lib.minecraft.bukkit.VersionName;
import mz.lib.minecraft.bukkit.wrappednms.NmsIChatBaseComponent;
import mz.lib.minecraft.bukkit.wrappednms.NmsIChatBaseComponent.NmsChatSerializer;
import mz.lib.minecraft.bukkit.wrapper.BukkitWrapper;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitClass;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitMethod;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitObject;
import mz.lib.wrapper.WrappedObject;

@WrappedBukkitClass(@VersionName("obc.util.CraftChatMessage"))
public interface ObcChatMessage extends WrappedBukkitObject
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
		if(BukkitWrapper.v17)
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
		if(BukkitWrapper.v17)
			return fromJSONComponentV17(json);
		else
			return fromComponentV13(NmsChatSerializer.jsonToComponent(json));
	}
	static String toJson(NmsIChatBaseComponent s)
	{
		return NmsChatSerializer.toJson(s);
	}
	@WrappedBukkitMethod(@VersionName(value="fromComponent",minVer=13))
	String staticFromComponentV13(NmsIChatBaseComponent cc);
	@WrappedBukkitMethod(@VersionName(value="fromStringOrNullToJSON",minVer=17))
	String staticFromStringOrNullToJSONV17(String s);
	@WrappedBukkitMethod(@VersionName(value="fromStringOrNull",minVer=13))
	NmsIChatBaseComponent staticFromStringOrNullV13(String s);
	@WrappedBukkitMethod(@VersionName(value="fromJSONComponent",minVer=17))
	String staticFromJSONComponentV17(String s);
}

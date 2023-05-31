package mz.lib.minecraft.bukkit.wrappednms;

import com.google.gson.JsonObject;
import mz.lib.minecraft.bukkit.VersionName;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitClass;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitMethod;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitObject;
import mz.lib.wrapper.WrappedObject;

@WrappedBukkitClass({@VersionName(value="nms.MojangsonParser",maxVer=17),@VersionName(value="net.minecraft.nbt.MojangsonParser",minVer=17)})
public interface NmsMojangsonParser extends WrappedBukkitObject
{
	static NmsNBTTagCompound parse(String json)
	{
		return WrappedObject.getStatic(NmsMojangsonParser.class).staticParse(json);
	}
	static JsonObject parseNonstandardJson(String json)
	{
		return parse(json).toJson();
	}
	@WrappedBukkitMethod({@VersionName("parse"),@VersionName(minVer=18, value="a")})
	NmsNBTTagCompound staticParse(String json);
}

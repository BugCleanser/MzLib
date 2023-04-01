package mz.lib.minecraft.bukkit.nms;

import com.google.gson.JsonObject;
import mz.lib.minecraft.wrapper.*;
import mz.lib.minecraft.VersionalName;
import mz.lib.wrapper.WrappedObject;
import mz.lib.wrapper.*;

@VersionalWrappedClass({@VersionalName(value="nms.MojangsonParser",maxVer=17),@VersionalName(value="net.minecraft.nbt.MojangsonParser",minVer=17)})
public interface NmsMojangsonParser extends VersionalWrappedObject
{
	static NmsNBTTagCompound parse(String json)
	{
		return WrappedObject.getStatic(NmsMojangsonParser.class).staticParse(json);
	}
	static JsonObject parseNonstandardJson(String json)
	{
		return parse(json).toJson();
	}
	@VersionalWrappedMethod({@VersionalName("parse"),@VersionalName(minVer=18, value="a")})
	NmsNBTTagCompound staticParse(String json);
}

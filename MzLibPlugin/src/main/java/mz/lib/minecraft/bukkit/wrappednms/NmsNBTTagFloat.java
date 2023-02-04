package mz.lib.minecraft.bukkit.wrappednms;

import com.google.gson.JsonPrimitive;
import mz.lib.minecraft.bukkit.VersionName;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitClass;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitMethod;
import mz.lib.wrapper.WrappedConstructor;
import mz.lib.wrapper.WrappedObject;

@WrappedBukkitClass({@VersionName(value="nms.NBTTagFloat",maxVer=17),@VersionName(value="net.minecraft.nbt.NBTTagFloat",minVer=17)})
public interface NmsNBTTagFloat extends NmsNBTTag
{
	static NmsNBTTagFloat newInstance(float value)
	{
		return WrappedObject.getStatic(NmsNBTTagFloat.class).staticNewInstance(value);
	}
	@WrappedConstructor
	NmsNBTTagFloat staticNewInstance(float value);
	@WrappedBukkitMethod({@VersionName("asFloat"),@VersionName(maxVer=17, value="i"),@VersionName(minVer=17, value="j")})
	float getValue();
	
	@Override
	default JsonPrimitive toJson()
	{
		return new JsonPrimitive(getValue());
	}
}

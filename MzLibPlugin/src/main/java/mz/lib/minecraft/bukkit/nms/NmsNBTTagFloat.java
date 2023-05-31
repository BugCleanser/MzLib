package mz.lib.minecraft.bukkit.nms;

import com.google.gson.JsonPrimitive;
import mz.lib.minecraft.nbt.*;
import mz.lib.minecraft.VersionalName;
import mz.lib.minecraft.wrapper.VersionalWrappedClass;
import mz.lib.minecraft.wrapper.VersionalWrappedMethod;
import mz.lib.wrapper.*;
import mz.mzlib.nbt.*;

@VersionalWrappedClass({@VersionalName(value="nms.NBTTagFloat",maxVer=17),@VersionalName(value="net.minecraft.nbt.NBTTagFloat",minVer=17)})
public interface NmsNBTTagFloat extends NmsNBTTag, NbtPrimitive
{
	static NmsNBTTagFloat newInstance(float value)
	{
		return WrappedObject.getStatic(NmsNBTTagFloat.class).staticNewInstance(value);
	}
	@WrappedConstructor
	NmsNBTTagFloat staticNewInstance(float value);
	@VersionalWrappedMethod({@VersionalName("asFloat"),@VersionalName(maxVer=17, value="i"),@VersionalName(minVer=17, value="j")})
	float getValue0();
	default Float getValue()
	{
		return getValue0();
	}
	
	@Override
	default JsonPrimitive toJson()
	{
		return new JsonPrimitive(getValue0());
	}
}

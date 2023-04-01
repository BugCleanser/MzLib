package mz.lib.minecraft.bukkit.nms;

import com.google.gson.JsonPrimitive;
import mz.lib.minecraft.nbt.*;
import mz.lib.minecraft.VersionalName;
import mz.lib.minecraft.wrapper.VersionalWrappedClass;
import mz.lib.minecraft.wrapper.VersionalWrappedMethod;
import mz.lib.wrapper.*;
import mz.lib.minecraft.nbt.*;

@VersionalWrappedClass({@VersionalName(value="nms.NBTTagLong",maxVer=17),@VersionalName(value="net.minecraft.nbt.NBTTagLong",minVer=17)})
public interface NmsNBTTagLong extends NmsNBTTag, NbtPrimitive
{
	static NmsNBTTagLong newInstance(long value)
	{
		return WrappedObject.getStatic(NmsNBTTagLong.class).staticNewInstance(value);
	}
	@WrappedConstructor
	NmsNBTTagLong staticNewInstance(long value);
	@VersionalWrappedMethod({@VersionalName("asLong"),@VersionalName(maxVer=18, value={"d"}),@VersionalName(minVer=18, value={"e"})})
	long getValue0();
	default Long getValue()
	{
		return getValue0();
	}
	
	@Override
	default JsonPrimitive toJson()
	{
		return new JsonPrimitive(getValue0());
	}
}

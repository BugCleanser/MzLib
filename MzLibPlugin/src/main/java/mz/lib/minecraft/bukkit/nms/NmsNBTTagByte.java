package mz.lib.minecraft.bukkit.nms;

import com.google.gson.JsonPrimitive;
import mz.lib.minecraft.nbt.*;
import mz.lib.minecraft.VersionalName;
import mz.lib.minecraft.wrapper.VersionalWrappedClass;
import mz.lib.minecraft.wrapper.VersionalWrappedMethod;
import mz.lib.wrapper.*;
import mz.lib.minecraft.nbt.*;

@VersionalWrappedClass({@VersionalName(value="nms.NBTTagByte",maxVer=17),@VersionalName(value="net.minecraft.nbt.NBTTagByte",minVer=17)})
public interface NmsNBTTagByte extends NmsNBTTag, NbtPrimitive
{
	static NmsNBTTagByte newInstance(byte value)
	{
		return WrappedObject.getStatic(NmsNBTTagByte.class).staticNewInstance(value);
	}
	@WrappedConstructor
	NmsNBTTagByte staticNewInstance(byte value);
	
	@VersionalWrappedMethod({@VersionalName("asByte"),@VersionalName(value="g",maxVer=13),@VersionalName(value="h",minVer=18)})
	byte getValue0();
	@Override
	default Byte getValue()
	{
		return getValue0();
	}
	
	@Override
	default JsonPrimitive toJson()
	{
		return new JsonPrimitive(getValue0());
	}
}

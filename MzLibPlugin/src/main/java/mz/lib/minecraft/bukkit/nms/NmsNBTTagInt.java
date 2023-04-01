package mz.lib.minecraft.bukkit.nms;

import com.google.gson.JsonPrimitive;
import mz.lib.minecraft.nbt.*;
import mz.lib.minecraft.wrapper.*;
import mz.lib.minecraft.VersionalName;
import mz.lib.wrapper.*;
import mz.lib.minecraft.nbt.*;
import mz.lib.wrapper.*;

@VersionalWrappedClass({@VersionalName(value="nms.NBTTagInt",maxVer=17),@VersionalName(value="net.minecraft.nbt.NBTTagInt",minVer=17)})
public interface NmsNBTTagInt extends NmsNBTTag, NbtPrimitive
{
	static NmsNBTTagInt newInstance(int value)
	{
		return WrappedObject.getStatic(NmsNBTTagInt.class).staticNewInstance(value);
	}
	@WrappedConstructor
	NmsNBTTagInt staticNewInstance(int value);
	@VersionalWrappedFieldAccessor(@VersionalName("@0"))
	int getValue0();
	default Integer getValue()
	{
		return getValue0();
	}
	
	@Override
	default JsonPrimitive toJson()
	{
		return new JsonPrimitive(getValue0());
	}
}

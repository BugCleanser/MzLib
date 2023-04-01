package mz.lib.minecraft.bukkit.nms;

import com.google.gson.JsonPrimitive;
import mz.lib.minecraft.nbt.*;
import mz.lib.minecraft.VersionalName;
import mz.lib.minecraft.wrapper.VersionalWrappedClass;
import mz.lib.minecraft.wrapper.VersionalWrappedMethod;
import mz.lib.wrapper.*;
import mz.lib.minecraft.nbt.*;

@VersionalWrappedClass({@VersionalName(value="nms.NBTTagShort",maxVer=17),@VersionalName(value="net.minecraft.nbt.NBTTagShort",minVer=17)})
public interface NmsNBTTagShort extends NmsNBTTag, NbtPrimitive
{
	static NmsNBTTagShort newInstance(short value)
	{
		return WrappedObject.getStatic(NmsNBTTagShort.class).staticNewInstance(value);
	}
	@WrappedConstructor
	NmsNBTTagShort staticNewInstance(short value);
	@VersionalWrappedMethod({@VersionalName("asShort"),@VersionalName(maxVer=17, value="f"),@VersionalName(minVer=17, value="g")})
	short getValue0();
	default Short getValue()
	{
		return getValue0();
	}
	
	@Override
	default JsonPrimitive toJson()
	{
		return new JsonPrimitive(getValue0());
	}
}

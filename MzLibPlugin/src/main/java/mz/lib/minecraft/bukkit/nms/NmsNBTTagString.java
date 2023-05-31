package mz.lib.minecraft.bukkit.nms;

import com.google.gson.JsonPrimitive;
import mz.lib.minecraft.VersionalName;
import mz.lib.minecraft.wrapper.VersionalWrappedClass;
import mz.lib.wrapper.*;

@VersionalWrappedClass({@VersionalName(value="nms.NBTTagString",maxVer=17),@VersionalName(value="net.minecraft.nbt.NBTTagString",minVer=17)})
public interface NmsNBTTagString extends NmsNBTTag
{
	static NmsNBTTagString newInstance(String value)
	{
		return WrappedObject.getStatic(NmsNBTTagString.class).staticNewInstance(value);
	}
	@WrappedConstructor
	NmsNBTTagString staticNewInstance(String value);
	@WrappedFieldAccessor("@0")
	String getValue();
	
	@Override
	default JsonPrimitive toJson()
	{
		return new JsonPrimitive(getValue());
	}
}

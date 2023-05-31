package mz.lib.minecraft.bukkit.wrappednms;

import com.google.gson.JsonPrimitive;
import mz.lib.minecraft.bukkit.VersionName;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitClass;
import mz.lib.wrapper.WrappedConstructor;
import mz.lib.wrapper.WrappedFieldAccessor;
import mz.lib.wrapper.WrappedObject;

@WrappedBukkitClass({@VersionName(value="nms.NBTTagString",maxVer=17),@VersionName(value="net.minecraft.nbt.NBTTagString",minVer=17)})
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

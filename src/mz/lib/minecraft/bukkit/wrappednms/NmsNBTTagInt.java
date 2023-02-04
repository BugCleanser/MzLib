package mz.lib.minecraft.bukkit.wrappednms;

import com.google.gson.JsonPrimitive;
import mz.lib.minecraft.bukkit.VersionName;
import mz.lib.minecraft.bukkit.wrapper.*;
import mz.lib.wrapper.WrappedConstructor;
import mz.lib.wrapper.WrappedObject;

@WrappedBukkitClass({@VersionName(value="nms.NBTTagInt",maxVer=17),@VersionName(value="net.minecraft.nbt.NBTTagInt",minVer=17)})
public interface NmsNBTTagInt extends NmsNBTTag
{
	static NmsNBTTagInt newInstance(int value)
	{
		return WrappedObject.getStatic(NmsNBTTagInt.class).staticNewInstance(value);
	}
	@WrappedConstructor
	NmsNBTTagInt staticNewInstance(int value);
	@WrappedBukkitFieldAccessor(@VersionName("@0"))
	int getValue();
	
	@Override
	default JsonPrimitive toJson()
	{
		return new JsonPrimitive(getValue());
	}
}

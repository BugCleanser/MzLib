package mz.lib.minecraft.bukkit.wrappednms;

import com.google.gson.JsonPrimitive;
import mz.lib.minecraft.bukkit.VersionName;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitClass;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitMethod;
import mz.lib.wrapper.WrappedConstructor;
import mz.lib.wrapper.WrappedObject;

@WrappedBukkitClass({@VersionName(value="nms.NBTTagLong",maxVer=17),@VersionName(value="net.minecraft.nbt.NBTTagLong",minVer=17)})
public interface NmsNBTTagLong extends NmsNBTTag
{
	static NmsNBTTagLong newInstance(long value)
	{
		return WrappedObject.getStatic(NmsNBTTagLong.class).staticNewInstance(value);
	}
	@WrappedConstructor
	NmsNBTTagLong staticNewInstance(long value);
	@WrappedBukkitMethod({@VersionName("asLong"),@VersionName(maxVer=18, value={"d"}),@VersionName(minVer=18, value={"e"})})
	long getValue();
	
	@Override
	default JsonPrimitive toJson()
	{
		return new JsonPrimitive(getValue());
	}
}

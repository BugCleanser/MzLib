package mz.lib.minecraft.bukkit.wrappednms;

import com.google.gson.JsonPrimitive;
import mz.lib.minecraft.bukkit.VersionName;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitClass;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitMethod;
import mz.lib.wrapper.WrappedConstructor;
import mz.lib.wrapper.WrappedObject;

@WrappedBukkitClass({@VersionName(value="nms.NBTTagByte",maxVer=17),@VersionName(value="net.minecraft.nbt.NBTTagByte",minVer=17)})
public interface NmsNBTTagByte extends NmsNBTTag
{
	static NmsNBTTagByte newInstance(byte value)
	{
		return WrappedObject.getStatic(NmsNBTTagByte.class).staticNewInstance(value);
	}
	@WrappedConstructor
	NmsNBTTagByte staticNewInstance(byte value);
	
	@WrappedBukkitMethod({@VersionName("asByte"),@VersionName(value="g",maxVer=13),@VersionName(value="h",minVer=18)})
	byte getValue();
	
	@Override
	default JsonPrimitive toJson()
	{
		return new JsonPrimitive(getValue());
	}
}

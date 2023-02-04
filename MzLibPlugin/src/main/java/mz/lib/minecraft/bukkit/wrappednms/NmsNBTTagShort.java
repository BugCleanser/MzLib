package mz.lib.minecraft.bukkit.wrappednms;

import com.google.gson.JsonPrimitive;
import mz.lib.minecraft.bukkit.VersionName;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitClass;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitMethod;
import mz.lib.wrapper.WrappedConstructor;
import mz.lib.wrapper.WrappedObject;

@WrappedBukkitClass({@VersionName(value="nms.NBTTagShort",maxVer=17),@VersionName(value="net.minecraft.nbt.NBTTagShort",minVer=17)})
public interface NmsNBTTagShort extends NmsNBTTag
{
	static NmsNBTTagShort newInstance(short value)
	{
		return WrappedObject.getStatic(NmsNBTTagShort.class).staticNewInstance(value);
	}
	@WrappedConstructor
	NmsNBTTagShort staticNewInstance(short value);
	@WrappedBukkitMethod({@VersionName("asShort"),@VersionName(maxVer=17, value="f"),@VersionName(minVer=17, value="g")})
	short getValue();
	
	@Override
	default JsonPrimitive toJson()
	{
		return new JsonPrimitive(getValue());
	}
}

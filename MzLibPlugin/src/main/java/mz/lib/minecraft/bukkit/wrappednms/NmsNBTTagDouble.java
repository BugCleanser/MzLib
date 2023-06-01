package mz.lib.minecraft.bukkit.wrappednms;

import com.google.gson.JsonPrimitive;
import mz.lib.minecraft.bukkit.VersionName;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitClass;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitMethod;
import mz.lib.wrapper.*;

@WrappedBukkitClass({@VersionName(value="nms.NBTTagDouble",maxVer=17),@VersionName(value="net.minecraft.nbt.NBTTagDouble",minVer=17)})
public interface NmsNBTTagDouble extends NmsNBTTag
{
	static NmsNBTTagDouble newInstance(double value)
	{
		return WrappedObject.getStatic(NmsNBTTagDouble.class).staticNewInstance(value);
	}
	@WrappedConstructor
	NmsNBTTagDouble staticNewInstance(double value);
	@WrappedBukkitMethod({@VersionName("asDouble"),@VersionName(maxVer=17, value="@0"),@VersionName(minVer=17, value="@0")})
	double getValue0();
	default Double getValue()
	{
		return getValue0();
	}

	@Override
	default JsonPrimitive toJson()
	{
		return new JsonPrimitive(getValue0());
	}
}

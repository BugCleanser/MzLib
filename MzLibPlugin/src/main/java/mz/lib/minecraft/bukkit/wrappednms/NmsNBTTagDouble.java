package mz.lib.minecraft.bukkit.nms;

import com.google.gson.JsonPrimitive;

import mz.lib.minecraft.nbt.*;

import mz.lib.minecraft.VersionalName;

import mz.lib.minecraft.wrapper.VersionalWrappedClass;

import mz.lib.minecraft.wrapper.VersionalWrappedMethod;

import mz.lib.wrapper.*;

import mz.mzlib.nbt.*;

@VersionalWrappedClass({@VersionalName(value="nms.NBTTagDouble",maxVer=17),@VersionalName(value="net.minecraft.nbt.NBTTagDouble",minVer=17)})

public interface NmsNBTTagDouble extends NmsNBTTag, NbtPrimitive

{

	static NmsNBTTagDouble newInstance(double value)	{

		return WrappedObject.getStatic(NmsNBTTagDouble.class).staticNewInstance(value);

	}

	@WrappedConstructor

	NmsNBTTagDouble staticNewInstance(double value);

	@VersionalWrappedMethod({@VersionalName("asDouble"),@VersionalName(maxVer=17, value="@0"),@VersionalName(minVer=17, value="@0")})

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

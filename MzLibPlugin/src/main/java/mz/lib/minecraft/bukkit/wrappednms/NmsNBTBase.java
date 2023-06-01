package mz.lib.minecraft.bukkit.wrappednms;

import com.google.gson.JsonElement;
import com.google.common.collect.Lists;
import mz.lib.minecraft.bukkit.VersionName;
import mz.lib.minecraft.bukkit.wrapper.*;
import mz.lib.wrapper.WrappedObject;

import java.util.ArrayList;
import java.util.List;

@WrappedBukkitClass({@VersionName(value="nms.NBTBase",maxVer=17),@VersionName(value="net.minecraft.nbt.NBTBase",minVer=17)})
public interface NmsNBTBase extends WrappedBukkitObject
{
	List<Class<? extends NmsNBTBase>> NBTWrappers=Lists.newArrayList(NmsNBTTagByte.class, NmsNBTTagCompound.class, NmsNBTTagInt.class, NmsNBTTagList.class, NmsNBTTagLong.class, NmsNBTTagShort.class, NmsNBTTagFloat.class, NmsNBTTagDouble.class, NmsNBTTagString.class);
	static NmsNBTBase wrap(Object nmsNbt)
	{
		for(Class<? extends NmsNBTBase> w: NBTWrappers)
		{
			if(WrappedObject.getRawClass(w).isAssignableFrom(nmsNbt.getClass()))
				return WrappedObject.wrap(w,nmsNbt);
		}
		throw new IllegalArgumentException("unknown type "+nmsNbt.getClass()+" of NBTBase");
	}
	JsonElement toJson();
}

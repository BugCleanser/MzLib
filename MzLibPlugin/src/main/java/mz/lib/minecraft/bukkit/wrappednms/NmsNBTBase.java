package mz.lib.minecraft.bukkit.wrappednms;

import com.google.gson.JsonElement;
import mz.lib.minecraft.bukkit.VersionName;
import mz.lib.minecraft.bukkit.wrapper.*;
import mz.lib.wrapper.WrappedObject;

import java.util.ArrayList;
import java.util.List;

@WrappedBukkitClass({@VersionName(value="nms.NBTBase",maxVer=17),@VersionName(value="net.minecraft.nbt.NBTBase",minVer=17)})
public interface NmsNBTBase extends WrappedBukkitObject
{
	List<Class<? extends NmsNBTBase>> NBTWrappers=new ArrayList<>();
	static NmsNBTBase wrap(Object nmsNbt)
	{
		if(NBTWrappers.isEmpty())
		{
			NBTWrappers.add(NmsNBTTagByte.class);
			NBTWrappers.add(NmsNBTTagCompound.class);
			NBTWrappers.add(NmsNBTTagInt.class);
			NBTWrappers.add(NmsNBTTagList.class);
			NBTWrappers.add(NmsNBTTagLong.class);
			NBTWrappers.add(NmsNBTTagShort.class);
			NBTWrappers.add(NmsNBTTagString.class);
		}
		for(Class<? extends NmsNBTBase> w: NBTWrappers)
		{
			if(WrappedObject.getRawClass(w).isAssignableFrom(nmsNbt.getClass()))
				return WrappedObject.wrap(w,nmsNbt);
		}
		throw new IllegalArgumentException("unknown type "+nmsNbt.getClass()+" of NBTBase");
	}
	JsonElement toJson();
}

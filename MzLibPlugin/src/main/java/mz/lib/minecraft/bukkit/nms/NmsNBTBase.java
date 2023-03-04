package mz.lib.minecraft.bukkit.nms;

import com.google.gson.JsonElement;
import mz.lib.minecraft.nbt.*;
import mz.lib.minecraft.wrapper.*;
import mz.lib.minecraft.VersionalName;
import mz.lib.wrapper.WrappedObject;

import java.util.*;
import java.util.function.*;

@VersionalWrappedClass({@VersionalName(value="nms.NBTBase",maxVer=17),@VersionalName(value="net.minecraft.nbt.NBTBase",minVer=17)})
public interface NmsNBTBase extends VersionalWrappedObject, NbtElement
{
	Map<Class<?>,Class<? extends NmsNBTBase>> NBTWrappers=new Supplier<Map<Class<?>,Class<? extends NmsNBTBase>>>()
	{
		@Override
		public Map<Class<?>,Class<? extends NmsNBTBase>> get()
		{
			Map<Class<?>,Class<? extends NmsNBTBase>> r=new HashMap<>();
			add(r,NmsNBTTagCompound.class);
			add(r,NmsNBTTagList.class);
			add(r,NmsNBTTagByteArray.class);
			add(r,NmsNBTTagIntArray.class);
			add(r,NmsNBTTagByte.class);
			add(r,NmsNBTTagShort.class);
			add(r,NmsNBTTagInt.class);
			add(r,NmsNBTTagLong.class);
			add(r,NmsNBTTagFloat.class);
			add(r,NmsNBTTagString.class);
			return r;
		}
		public void add(Map<Class<?>,Class<? extends NmsNBTBase>> r,Class<? extends NmsNBTBase> wrapper)
		{
			r.put(WrappedObject.getRawClass(wrapper),wrapper);
		}
	}.get();
	static NmsNBTBase wrap(Object nmsNbt)
	{
		if(nmsNbt==null)
			return null;
		return WrappedObject.wrap(NBTWrappers.get(nmsNbt.getClass()),nmsNbt);
	}
	JsonElement toJson();
}

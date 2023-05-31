package mz.lib.minecraft.bukkit.wrappednms;

import com.google.gson.JsonObject;
import mz.lib.minecraft.bukkit.VersionName;
import mz.lib.minecraft.bukkit.wrapper.BukkitWrapper;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitClass;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitFieldAccessor;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitMethod;
import mz.lib.wrapper.WrappedConstructor;
import mz.lib.wrapper.WrappedMethod;
import mz.lib.wrapper.WrappedObject;
import org.bukkit.NamespacedKey;

import java.io.DataInput;
import java.io.DataOutput;
import java.util.List;
import java.util.Map;

@WrappedBukkitClass({@VersionName(value="nms.NBTTagCompound",maxVer=17),@VersionName(value="net.minecraft.nbt.NBTTagCompound",minVer=17)})
public interface NmsNBTTagCompound extends NmsNBTTag
{
	static NmsNBTTagCompound newInstance()
	{
		return WrappedObject.getStatic(NmsNBTTagCompound.class).staticNewInstance();
	}
	static NmsNBTTagCompound newInstance(String json)
	{
		return NmsMojangsonParser.parse(json);
	}
	static NmsNBTTagTypeV15 getTypeV15()
	{
		return WrappedObject.getStatic(NmsNBTTagCompound.class).staticGetTypeV15();
	}
	static NmsNBTTagCompound read(DataInput s)
	{
		if(BukkitWrapper.version<15)
		{
			NmsNBTTagCompound r=NmsNBTTagCompound.newInstance();
			r.loadV_15(s,128,NmsNBTReadLimiter.newInstance(Long.MAX_VALUE));
			return r;
		}
		else
		{
			return getTypeV15().read(s,128,NmsNBTReadLimiter.newInstance(Long.MAX_VALUE)).cast(NmsNBTTagCompound.class);
		}
	}
	@WrappedConstructor
	NmsNBTTagCompound staticNewInstance();
	@WrappedBukkitFieldAccessor({@VersionName(minVer=15,maxVer=16, value="a"),@VersionName(minVer=16, value="b")})
	NmsNBTTagTypeV15 staticGetTypeV15();
	@WrappedBukkitFieldAccessor({@VersionName("map"),@VersionName(minVer=17, value="x")})
	Map<String,Object> getMap();
	default NmsNBTTagCompound set(String key,NmsNBTTag value)
	{
		return set(key,(NmsNBTBase)value);
	}
	default NmsNBTTagCompound set(String key,NmsNBTBase value)
	{
		getMap().put(key,value.getRaw());
		return this;
	}
	default NmsNBTTagCompound set(String key,NamespacedKey value)
	{
		return set(key,NmsNBTTagString.newInstance(value.toString()));
	}
	default NmsNBTTagCompound set(String key,NmsRecipeItemStack value)
	{
		return set(key,NmsNBTTagList.newInstance(value));
	}
	default NmsNBTTagCompound set(String key,String value)
	{
		return set(key,NmsNBTTagString.newInstance(value));
	}
	default NmsNBTTagCompound set(String key,List<? extends NmsNBTBase> value)
	{
		return set(key,NmsNBTTagList.newInstance(value));
	}
	default NmsNBTTagCompound set(String key,int value)
	{
		return set(key,NmsNBTTagInt.newInstance(value));
	}
	@WrappedMethod(value={"write","a"})
	void write(DataOutput s);
	@WrappedBukkitMethod(value={@VersionName(value="load",maxVer=15)})
	void loadV_15(DataInput s,int depth,NmsNBTReadLimiter limiter);
	default boolean containsKey(String key)
	{
		return getMap().containsKey(key);
	}
	default boolean containsValue(WrappedObject value)
	{
		return getMap().containsValue(value.getRaw());
	}
	default NmsNBTTagCompound remove(String key)
	{
		getMap().remove(key);
		return this;
	}
	
	default boolean getBool(String key)
	{
		try
		{
			return getByte(key)!=0;
		}
		catch(ClassCastException e)
		{
			try
			{
				return getInt(key)!=0;
			}
			catch(ClassCastException e1)
			{
				return getShort(key)!=0;
			}
		}
	}
	default boolean getBool(String key,boolean def)
	{
		if(containsKey(key))
			return getBool(key);
		return def;
	}
	default String getString(String key)
	{
		return WrappedObject.wrap(NmsNBTTagString.class,getMap().get(key)).getValue();
	}
	default short getShort(String key)
	{
		try
		{
			return WrappedObject.wrap(NmsNBTTagShort.class,getMap().get(key)).getValue();
		}
		catch(ClassCastException e)
		{
			try
			{
				return (short) getInt(key);
			}
			catch(ClassCastException e1)
			{
				try
				{
					return getByte(key);
				}
				catch(ClassCastException e2)
				{
					return (short) getLong(key);
				}
			}
		}
	}
	default float getFloat(String key)
	{
		return WrappedObject.wrap(NmsNBTTagFloat.class,getMap().get(key)).getValue();
	}
	default int getInt(String key)
	{
		try
		{
			return WrappedObject.wrap(NmsNBTTagInt.class,getMap().get(key)).getValue();
		}
		catch(ClassCastException e)
		{
			try
			{
				return getShort(key);
			}
			catch(ClassCastException e1)
			{
				try
				{
					return getByte(key);
				}
				catch(ClassCastException e2)
				{
					try
					{
						return (int) getLong(key);
					}
					catch(Throwable e3)
					{
						throw e;
					}
				}
			}
		}
	}
	default long getLong(String key)
	{
		try
		{
			return WrappedObject.wrap(NmsNBTTagLong.class,getMap().get(key)).getValue();
		}
		catch(ClassCastException e)
		{
			try
			{
				return getInt(key);
			}
			catch(ClassCastException e1)
			{
				try
				{
					return getShort(key);
				}
				catch(ClassCastException e2)
				{
					return getByte(key);
				}
			}
		}
	}
	default <T extends NmsNBTBase> T get(String key,Class<T> wrapper)
	{
		return WrappedObject.wrap(wrapper,getMap().get(key));
	}
	default byte getByte(String key)
	{
		try
		{
			return get(key,NmsNBTTagByte.class).getValue();
		}
		catch(ClassCastException e)
		{
			try
			{
				return (byte) getInt(key);
			}
			catch(ClassCastException e1)
			{
				try
				{
					return (byte) getShort(key);
				}
				catch(ClassCastException e2)
				{
					return (byte) getLong(key);
				}
			}
		}
	}
	default NamespacedKey getNamespacedKey(String key)
	{
		return NmsMinecraftKey.newInstance(getString(key)).toBukkit();
	}
	default NmsNBTTagCompound getCompound(String key)
	{
		return WrappedObject.wrap(NmsNBTTagCompound.class,getMap().get(key));
	}
	default NmsNBTTagList getList(String key)
	{
		return WrappedObject.wrap(NmsNBTTagList.class,getMap().get(key));
	}
	
	default JsonObject toJson()
	{
		JsonObject r=new JsonObject();
		getMap().forEach((k,v)->r.add(k,NmsNBTBase.wrap(v).toJson()));
		return r;
	}
}

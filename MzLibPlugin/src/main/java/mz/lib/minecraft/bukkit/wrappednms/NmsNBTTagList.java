package mz.lib.minecraft.bukkit.wrappednms;

import com.google.gson.JsonArray;
import mz.lib.minecraft.bukkit.VersionName;
import mz.lib.minecraft.bukkit.itemstack.ItemStackBuilder;
import mz.lib.minecraft.bukkit.wrapper.BukkitWrapper;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitClass;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitFieldAccessor;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitMethod;
import mz.lib.wrapper.WrappedConstructor;
import mz.lib.wrapper.WrappedMethod;
import mz.lib.wrapper.WrappedObject;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.stream.Collectors;

@WrappedBukkitClass({@VersionName(value="nms.NBTTagList",maxVer=17),@VersionName(value="net.minecraft.nbt.NBTTagList",minVer=17)})
public interface NmsNBTTagList extends NmsNBTTag
{
	static NmsNBTTagList newInstance()
	{
		return WrappedObject.getStatic(NmsNBTTagList.class).staticNewInstance();
	}
	static <T extends NmsNBTBase> NmsNBTTagList newInstance(List<T> l)
	{
		NmsNBTTagList r=newInstance();
		l.forEach(r::add);
		return r;
	}
	static <T extends NmsNBTBase> NmsNBTTagList newInstance(T ...l)
	{
		NmsNBTTagList r=newInstance();
		for(T i:l)
			r.add(i);
		return r;
	}
	static NmsNBTTagList wrapValues(List<?> values)
	{
		if(values==null)
			return null;
		NmsNBTTagList r=NmsNBTTagList.newInstance();
		values.forEach(v->
		{
			r.add(NmsNBTTag.wrapValue(v));
		});
		return r;
	}
	
	static NmsNBTTagList newInstance(NmsRecipeItemStack value)
	{
		return NmsNBTTagList.newInstance(value==null?new ArrayList<>():value.getChoices().stream().map(is->NmsNBTTagCompound.newInstance().set("raw",new ItemStackBuilder(is).save())).collect(Collectors.toList()));
	}
	default NmsRecipeItemStack toRecipeItemStack()
	{
		if(this.size()==0)
			return NmsRecipeItemStack.air();
		return NmsRecipeItemStack.hybrid(this.values(NmsNBTTagCompound.class).stream().map(n->new ItemStackBuilder(n.getCompound("raw")).get()).collect(Collectors.toList()).toArray(new ItemStack[0]));
	}
	
	@WrappedConstructor
	NmsNBTTagList staticNewInstance();
	@Deprecated
	@WrappedBukkitFieldAccessor({@VersionName("list"),@VersionName(minVer=17, value="c")})
	List<Object> getList();
	@WrappedMethod("size")
	int size();
	@WrappedBukkitMethod({@VersionName(value="add",minVer=14),@VersionName(minVer=14,value="c")})
	void addV14(int i,NmsNBTBase nbt);
	default boolean addV14(NmsNBTBase nbt)
	{
		addV14(size(),nbt);
		return true;
	}
	@WrappedBukkitMethod(@VersionName(value="add",maxVer=14))
	void addV_14(NmsNBTBase nbt);
	default boolean add(NmsNBTBase nbt)
	{
		if(BukkitWrapper.version>=14)
			addV14(nbt);
		else
			addV_14(nbt);
		return true;
	}
	default <T extends NmsNBTBase> NmsNBTTagList addAll(Collection<T> nbt)
	{
		nbt.forEach(n->add(n));
		return this;
	}
	@WrappedBukkitMethod({@VersionName(maxVer=13, value="i"),@VersionName(value="get",minVer=13,maxVer=18),@VersionName(minVer=18, value="k")})
	NmsNBTBase get(int index);
	default <T extends NmsNBTBase> T get(int index,Class<T> type)
	{
		return get(index).cast(type);
	}
	@WrappedBukkitMethod(@VersionName(maxVer=13, value="a"))
	void setV_13(int index,NmsNBTBase nbt);
	@WrappedBukkitMethod({@VersionName(minVer=13,value="set"),@VersionName(minVer=18, value="d")})
	NmsNBTBase setV13(int index,NmsNBTBase nbt);
	default void set(int index,NmsNBTBase nbt)
	{
		if(BukkitWrapper.v13)
			setV13(index,nbt);
		else
			setV_13(index,nbt);
	}
	default boolean remove(NmsNBTBase nbt)
	{
		return getList().remove(nbt.getRaw());
	}
	@WrappedBukkitMethod({@VersionName("remove"),@VersionName(minVer=18, value="c")})
	NmsNBTBase remove(int index);
	default <T extends WrappedObject> T remove(int index,Class<T> type)
	{
		return WrappedObject.wrap(type,remove(index).getRaw());
	}
	default List<NmsNBTBase> values()
	{
		return new AbstractList<NmsNBTBase>()
		{
			@Override
			public NmsNBTBase get(int index)
			{
				return NmsNBTTagList.this.get(index);
			}
			@Override
			public NmsNBTBase set(int index,NmsNBTBase element)
			{
				NmsNBTBase last=get(index);
				NmsNBTTagList.this.set(index,element);
				return last;
			}
			@Override
			public NmsNBTBase remove(int index)
			{
				return NmsNBTTagList.this.remove(index);
			}
			@Override
			public void add(int index,NmsNBTBase element)
			{
				if(BukkitWrapper.v13)
					NmsNBTTagList.this.addV14(index,element);
				else
					throw new UnsupportedOperationException();
			}
			@Override
			public boolean add(NmsNBTBase e)
			{
				return NmsNBTTagList.this.add(e);
			}
			@Override
			public int size()
			{
				return NmsNBTTagList.this.size();
			}
			@Override
			public boolean remove(Object o)
			{
				return NmsNBTTagList.this.remove((NmsNBTBase) o);
			}
		};
	}
	default <T extends NmsNBTBase> List<T> values(Class<T> wrapper)
	{
		return new AbstractList<T>()
		{
			@Override
			public T get(int index)
			{
				return NmsNBTTagList.this.get(index,wrapper);
			}
			@Override
			public T set(int index,T element)
			{
				T last=get(index);
				NmsNBTTagList.this.set(index,element);
				return last;
			}
			@Override
			public T remove(int index)
			{
				return NmsNBTTagList.this.remove(index,wrapper);
			}
			@Override
			public void add(int index,T element)
			{
				if(BukkitWrapper.v13)
					NmsNBTTagList.this.addV14(index,element);
				else if(index==size())
					NmsNBTTagList.this.addV_14(element);
				else
					throw new UnsupportedOperationException();
			}
			@Override
			public boolean add(T e)
			{
				return NmsNBTTagList.this.add(e);
			}
			@Override
			public int size()
			{
				return NmsNBTTagList.this.size();
			}
			@Override
			public boolean remove(Object o)
			{
				return NmsNBTTagList.this.remove((NmsNBTBase) o);
			}
		};
	}
	default List<String> wrapStringList()
	{
		return new AbstractList<String>()
		{
			@Override
			public String get(int index)
			{
				return NmsNBTTagList.this.get(index,NmsNBTTagString.class).getValue();
			}
			@Override
			public String set(int index,String element)
			{
				String last=get(index);
				NmsNBTTagList.this.set(index,NmsNBTTagString.newInstance(element));
				return last;
			}
			@Override
			public String remove(int index)
			{
				return NmsNBTTagList.this.remove(index,NmsNBTTagString.class).getValue();
			}
			@Override
			public void add(int index,String element)
			{
				if(BukkitWrapper.v13)
					NmsNBTTagList.this.addV14(index,NmsNBTTagString.newInstance(element));
				else
					throw new UnsupportedOperationException();
			}
			@Override
			public boolean add(String e)
			{
				return NmsNBTTagList.this.add(NmsNBTTagString.newInstance(e));
			}
			@Override
			public int size()
			{
				return NmsNBTTagList.this.size();
			}
			@Override
			public boolean remove(Object o)
			{
				return NmsNBTTagList.this.remove(NmsNBTTagString.newInstance((String) o));
			}
		};
	}
	
	@Override
	default JsonArray toJson()
	{
		JsonArray r=new JsonArray();
		this.getList().forEach(nmsNbt->
		{
			r.add(NmsNBTBase.wrap(nmsNbt).toJson());
		});
		return r;
	}
}

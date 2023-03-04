package mz.lib.minecraft.bukkit.nms;

import com.google.gson.JsonArray;
import mz.lib.minecraft.*;
import mz.lib.minecraft.nbt.*;
import mz.lib.minecraft.VersionalName;
import mz.lib.minecraft.bukkitlegacy.itemstack.ItemStackBuilder;
import mz.lib.minecraft.wrapper.VersionalWrappedClass;
import mz.lib.minecraft.wrapper.VersionalWrappedFieldAccessor;
import mz.lib.minecraft.wrapper.VersionalWrappedMethod;
import mz.lib.wrapper.WrappedConstructor;
import mz.lib.wrapper.WrappedMethod;
import mz.lib.wrapper.WrappedObject;

import java.util.*;
import java.util.stream.Collectors;

@VersionalWrappedClass({@VersionalName(value="nms.NBTTagList",maxVer=17),@VersionalName(value="net.minecraft.nbt.NBTTagList",minVer=17)})
public interface NmsNBTTagList extends NmsNBTTag, NbtList
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
	
	@WrappedConstructor
	NmsNBTTagList staticNewInstance();
	@Deprecated
	@VersionalWrappedFieldAccessor({@VersionalName("list"),@VersionalName(minVer=17, value="c")})
	List<Object> getList();
	@WrappedMethod("size")
	int size();
	@VersionalWrappedMethod({@VersionalName(value="add",minVer=14),@VersionalName(minVer=14,value="c")})
	void addV14(int i,NmsNBTBase nbt);
	default boolean addV14(NmsNBTBase nbt)
	{
		addV14(size(),nbt);
		return true;
	}
	@VersionalWrappedMethod(@VersionalName(value="add",maxVer=14))
	void addV_14(NmsNBTBase nbt);
	default boolean add(NmsNBTBase nbt)
	{
		if(Server.instance.version>=14)
			addV14(nbt);
		else
			addV_14(nbt);
		return true;
	}
	
	@Override
	default void add(NbtElement element)
	{
		add((NmsNBTBase)element);
	}
	
	default <T extends NmsNBTBase> NmsNBTTagList addAll(Collection<T> nbt)
	{
		nbt.forEach(n->add(n));
		return this;
	}
	@VersionalWrappedMethod({@VersionalName(maxVer=13, value="i"),@VersionalName(value="get",minVer=13,maxVer=18),@VersionalName(minVer=18, value="k")})
	NmsNBTBase get(int index);
	default <T extends NmsNBTBase> T get(int index,Class<T> type)
	{
		return get(index).cast(type);
	}
	@VersionalWrappedMethod(@VersionalName(maxVer=13, value="a"))
	void setV_13(int index,NmsNBTBase nbt);
	@VersionalWrappedMethod({@VersionalName(minVer=13,value="set"),@VersionalName(minVer=18, value="d")})
	NmsNBTBase setV13(int index,NmsNBTBase nbt);
	default void set(int index,NmsNBTBase nbt)
	{
		if(Server.instance.v13)
			setV13(index,nbt);
		else
			setV_13(index,nbt);
	}
	
	@Override
	default void set(int index,NbtElement value)
	{
		set(index,(NmsNBTBase)value);
	}
	
	default boolean remove(NmsNBTBase nbt)
	{
		return getList().remove(nbt.getRaw());
	}
	@VersionalWrappedMethod({@VersionalName("remove"),@VersionalName(minVer=18, value="c")})
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
				if(Server.instance.v13)
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
				if(Server.instance.v13)
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
				if(Server.instance.v13)
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

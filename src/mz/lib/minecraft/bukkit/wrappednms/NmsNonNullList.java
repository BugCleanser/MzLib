package mz.lib.minecraft.bukkit.wrappednms;

import mz.lib.minecraft.bukkit.VersionName;
import mz.lib.minecraft.bukkit.wrapper.*;
import mz.lib.wrapper.WrappedObject;

import java.util.*;
import java.util.stream.*;

@WrappedBukkitClass({@VersionName(value="nms.NonNullList",maxVer=17),@VersionName(value="net.minecraft.core.NonNullList",minVer=17)})
public interface NmsNonNullList extends WrappedBukkitObject,Iterable<WrappedObject>
{
	@Override
	AbstractList<Object> getRaw();
	
	default <T extends WrappedObject> T get(int i,Class<T> w)
	{
		return WrappedObject.wrap(w,getRaw().get(i));
	}
	default void set(int i,WrappedObject value)
	{
		getRaw().set(i,value.getRaw());
	}
	default void add(int i,WrappedObject value)
	{
		getRaw().add(i,value.getRaw());
	}
	default void add(WrappedObject value)
	{
		getRaw().add(value.getRaw());
	}
	default Iterator<WrappedObject> iterator()
	{
		Iterator<Object> r=getRaw().iterator();
		return new Iterator<WrappedObject>()
		{
			@Override
			public boolean hasNext()
			{
				return r.hasNext();
			}
			@Override
			public WrappedObject next()
			{
				return WrappedObject.wrap(WrappedObject.class,r.next());
			}
		};
	}
	default <T extends WrappedObject> Iterator<T> iterator(Class<T> w)
	{
		Iterator<Object> r=getRaw().iterator();
		return new Iterator<T>()
		{
			@Override
			public boolean hasNext()
			{
				return r.hasNext();
			}
			@Override
			public T next()
			{
				return WrappedObject.wrap(w,r.next());
			}
		};
	}
	
	default <T extends WrappedObject> List<T> toList(Class<T> wrapper)
	{
		return getRaw().stream().map(i->WrappedObject.wrap(wrapper,i)).collect(Collectors.toList());
	}
	
	@WrappedBukkitMethod(@VersionName("a"))
	NmsNonNullList staticNewInstance();
	static NmsNonNullList newInstance()
	{
		return WrappedObject.getStatic(NmsNonNullList.class).staticNewInstance();
	}
	static NmsNonNullList newInstance(List<? extends WrappedObject> list)
	{
		NmsNonNullList r=newInstance();
		for(WrappedObject e:list)
			r.add(e);
		return r;
	}
}

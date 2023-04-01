package mz.lib.minecraft.bukkit.nms;

import mz.lib.minecraft.wrapper.*;
import mz.lib.minecraft.VersionalName;
import mz.lib.wrapper.WrappedObject;
import mz.lib.wrapper.*;

import java.util.*;
import java.util.stream.*;

@VersionalWrappedClass({@VersionalName(value="nms.NonNullList",maxVer=17),@VersionalName(value="net.minecraft.core.NonNullList",minVer=17)})
public interface NmsNonNullList extends VersionalWrappedObject,Iterable<WrappedObject>
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
	
	@VersionalWrappedMethod(@VersionalName("a"))
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

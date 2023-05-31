package mz.lib.nothing;

import mz.lib.*;
import mz.lib.wrapper.WrappedObject;

import java.lang.reflect.Method;
import java.util.Optional;

public interface Nothing
{
	IncrementalIdMap<Object> data=new IncrementalIdMap<>();
	static void init()
	{
		Ref<Class<?>> nothingData=new Ref<>(null);
		if(TypeUtil.hasThrowable(()->nothingData.set(Class.forName("mz.lib.nothing.NothingData",false,ClassUtil.extClassLoader))))
		{
			nothingData.set(ClassUtil.loadClass("mz.lib.nothing.NothingData",FileUtil.readInputStream(Nothing.class.getClassLoader().getResourceAsStream("mz/lib/nothing/NothingData.class")),ClassUtil.extClassLoader));
		}
		try
		{
			nothingData.get().getDeclaredField("data").set(null,data.store);
		}
		catch(Throwable e)
		{
			throw TypeUtil.throwException(e);
		}
	}
	
	static <T extends Nothing&WrappedObject> void install(Class<T> nothing)
	{
		Class<?> rc=WrappedObject.getRawClass(nothing);
		if(!NothingClass.classes.containsKey(rc))
			NothingClass.classes.put(rc,new NothingClass(rc));
		NothingClass.classes.get(rc).install(nothing);
	}
	static <T extends Nothing&WrappedObject> void uninstall(Class<T> nothing)
	{
		Class<?> rc=WrappedObject.getRawClass(nothing);
		if(NothingClass.classes.containsKey(rc))
		{
			NothingClass.classes.get(rc).uninstall(nothing);
			if(NothingClass.classes.get(rc).installedNothings.isEmpty())
				NothingClass.classes.remove(rc);
		}
	}
	
	static <T> Optional<T> doReturn(T value)
	{
		return Optional.ofNullable(value);
	}
	static <T> Optional<T> doContinue()
	{
		return TypeUtil.cast(null);
	}
	
	static void uninstallAll()
	{
		for(NothingClass nc:NothingClass.classes.values())
			nc.uninstallAll();
		NothingClass.classes.clear();
	}
	
	default NothingInject[] getInjects(Method method)
	{
		return method.getDeclaredAnnotationsByType(NothingInject.class);
	}
}

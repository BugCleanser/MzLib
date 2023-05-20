package mz.mzlib.module;

import mz.mzlib.javautil.ClassUtil;
import mz.mzlib.javautil.CopyOnWriteSet;
import mz.mzlib.javautil.RuntimeUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public abstract class MzModule
{
	public boolean isLoaded=false;
	public Set<MzModule> submodules=new CopyOnWriteSet<>();
	public Map<Object,List<IRegistrar<?>>> registeredObjects=new ConcurrentHashMap<>();
	
	public void register(Object object)
	{
		if(!isLoaded)
			throw new IllegalStateException("Try to register an object but the module is not loaded: "+this+".");
		if(object instanceof MzModule)
		{
			if(((MzModule)object).isLoaded)
				throw new IllegalStateException("Try to load the module but it has been loaded: "+object+".");
			((MzModule)object).isLoaded=true;
			submodules.add((MzModule)object);
			((MzModule)object).onLoad();
		}
		if(registeredObjects.containsKey(object))
			throw new IllegalStateException("Try to register the object but it has been registered: "+object+".");
		List<IRegistrar<?>> registers=new ArrayList<>();
		ClassUtil.forEachSuper(object.getClass(),c->
		{
			for(IRegistrar<?> i:RegistrarRegistrar.instance.registrars.get(c))
				if(i.isRegistrable(object))
				{
					i.register(this,RuntimeUtil.forceCast(object));
					registers.add(i);
				}
		});
		if(registers.isEmpty()&&!(object instanceof MzModule))
			throw new UnsupportedOperationException("Try to register the object but found no registrar: "+object+".");
		registeredObjects.put(object,registers);
	}
	public void unregister(Object object)
	{
		if(!isLoaded)
			throw new IllegalStateException("Try to unregister an object but the module has been unloaded: "+this+".");
		if(object instanceof MzModule)
		{
			if(!((MzModule)object).isLoaded)
				throw new IllegalStateException("Try to unload the module but it's not loaded: "+object+".");
			if(!submodules.contains(object))
				throw new IllegalStateException("Try to unload the module("+object+") but it's not loaded by this module("+this+").");
			((MzModule)object).isLoaded=false;
			submodules.remove(object);
			((MzModule)object).onUnload();
			for(MzModule i:new HashSet<>(((MzModule)object).submodules))
				((MzModule)object).unregister(i);
			for(Object i:new HashSet<>(((MzModule)object).registeredObjects.keySet()))
				((MzModule)object).unregister(i);
		}
		List<IRegistrar<?>> removed=registeredObjects.remove(object);
		if(removed==null)
			throw new IllegalStateException("Try to unregister the object but it's not registered: "+object+".");
		for(IRegistrar<?> i:removed)
		{
			Set<IRegistrar<?>> key=RegistrarRegistrar.instance.registrars.get(i.getType());
			if(key==null||!key.contains(i))
				throw new IllegalStateException("Try to unregister an object("+object+") but the registrar("+i+") has been unloaded.");
			i.unregister(this,RuntimeUtil.forceCast(object));
		}
	}
	
	public void onLoad()
	{
	}
	public void onUnload()
	{
	}
}

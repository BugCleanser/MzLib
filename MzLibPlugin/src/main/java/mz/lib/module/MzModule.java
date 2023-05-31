package mz.lib.module;

import io.github.karlatemp.unsafeaccessor.*;
import mz.lib.*;
import mz.lib.event.EventListener;

import java.lang.invoke.*;
import java.lang.reflect.*;
import java.util.*;
import java.util.concurrent.*;

public abstract class MzModule
{
	public static Set<MzModule> loadedModules=new ConcurrentHashSet<>();
	public List<MzModule> depends=new CopyOnWriteArrayList<>();
	public List<MzModule> submodules=new CopyOnWriteArrayList<>();
	public Map<?,Set<IRegistrar<?>>> registrars=new ConcurrentHashMap<>();
	
	public void onLoad()
	{
	}
	public void onUnload()
	{
	}
	
	public boolean isLoaded()
	{
		return loadedModules.contains(this);
	}
	public void load()
	{
		if(isLoaded())
			return;
		loadedModules.add(this);
		for(Method m:this.getClass().getMethods())
		{
			EventHandler a=m.getDeclaredAnnotation(EventHandler.class);
			if(a!=null)
			{
				MethodHandle mh=ClassUtil.unreflect(m);
				reg(new EventListener<>(TypeUtil.cast(m.getParameterTypes()[0]),a.order(),e->
				{
					try
					{
						mh.invokeWithArguments(this,e);
					}
					catch(Throwable ex)
					{
						throw TypeUtil.throwException(ex);
					}
				}));
			}
		}
		onLoad();
	}
	public List<MzModule> unload()
	{
		if(!isLoaded())
			return new ArrayList<>();
		onUnload();
		List<MzModule> r=new ArrayList<>();
		r.add(this);
		for(MzModule i:submodules)
			r.addAll(i.unload());
		submodules.clear();
		for(MzModule m:loadedModules)
			if(m.depends.contains(this))
				r.addAll(m.unload());
		registrars.forEach((i,j)->j.forEach(k->k.unregister(this,TypeUtil.cast(i))));
		registrars.clear();
		loadedModules.remove(this);
		depends.clear();
		return r;
	}
	
	public void depend(MzModule support)
	{
		this.depends.add(support);
	}
	
	public List<MzModule> getSubmodules()
	{
		return submodules;
	}
	
	public void reg(Object obj)
	{
		int regCount=0;
		if(obj instanceof MzModule)
		{
			getSubmodules().add((MzModule)obj);
			((MzModule)obj).load();
			regCount++;
		}
		for(Class<?> i:ClassUtil.getSuperClasses(obj.getClass()))
		{
			Set<IRegistrar<?>> r=RegistrarRegistrar.instance.getRegisteredRegistrars().get(i);
			if(r==null)
				continue;
			for(IRegistrar<?> j:r)
				if(j.register(this,TypeUtil.cast(obj)))
				{
					registrars.computeIfAbsent(TypeUtil.cast(obj),k->new ConcurrentHashSet<>()).add(j);
					regCount++;
				}
		}
		if(regCount==0)
			throw new UnsupportedOperationException("Failed to reg "+obj+".");
	}
	public boolean unreg(Object obj)
	{
		Set<IRegistrar<?>> v=registrars.get(obj);
		if(v!=null)
		{
			for(IRegistrar<?> r: v)
				r.unregister(this,TypeUtil.cast(obj));
			registrars.remove(obj);
			return true;
		}
		return false;
	}
	
	public boolean hasReged(Object obj)
	{
		return registrars.containsKey(obj);
	}
	
	public Set<MzModule> getLoadedModules()
	{
		return loadedModules;
	}
}

package mz.lib.minecraft.bukkit.module;

import com.google.common.collect.Lists;
import mz.lib.ClassUtil;
import mz.lib.ListUtil;
import mz.lib.Ref;
import mz.lib.TypeUtil;
import mz.lib.minecraft.bukkit.MzLib;
import mz.lib.minecraft.bukkit.event.ModuleDisableEvent;
import mz.lib.minecraft.bukkit.event.ModuleEnableEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.plugin.Plugin;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public interface IModule extends Listener
{
	Plugin getPlugin();
	Set<IModule> getDepends();
	default void depend(IModule m)
	{
		getDepends().add(m);
	}
	default Set<IModule> getAllDepends()
	{
		Set<IModule> r=new HashSet<>(getDepends());
		for(IModule depend:getDepends())
		{
			r.addAll(depend.getAllDepends());
		}
		return r;
	}
	default String getName()
	{
		return this.getClass().getName();
	}
	default void onEnable()
	{
	}
	default void onDisable()
	{
	}
	
	Map<Plugin,List<IModule>> modules=new ConcurrentHashMap<>();
	
	Map<IRegistrar<?>,List<Object>> getRegisteredObjects();
	Ref<Boolean> getEnabledRef();
	default boolean isEnabled()
	{
		return getEnabledRef().get();
	}
	default void reg(Object obj)
	{
		synchronized(this)
		{
			List<? extends IRegistrar<?>> registrars=IRegistrar.getRegistrars(obj.getClass());
			int reged=0;
			for(IRegistrar<?> r:registrars)
			{
				if(r.register(this,TypeUtil.cast(obj)))
				{
					reged++;
					if(r instanceof IModule && r!=this && !this.getAllDepends().contains(r))
					{
						MzLib.instance.getLogger().warning("The module "+this.getClass().getName()+" should depends module "+r.getClass().getName());
					}
					List<Object> list=getRegisteredObjects().computeIfAbsent(r,k->new LinkedList<>());
					list.add(obj);
				}
			}
			if(reged==0)
				MzLib.instance.getLogger().warning("The object can't be reged: "+obj);
		}
	}
	default void unreg(Object obj)
	{
		synchronized(this)
		{
			for(Map.Entry<IRegistrar<?>,List<Object>> e: getRegisteredObjects().entrySet())
			{
				if(e.getValue().remove(obj))
				{
					e.getKey().unregister(TypeUtil.cast(obj));
					if(e.getValue().isEmpty())
						getRegisteredObjects().remove(e.getKey());
					return;
				}
			}
		}
	}
	default void enable()
	{
		synchronized(this)
		{
			if(isEnabled())
				return;
			ModuleEnableEvent event=new ModuleEnableEvent(this);
			Bukkit.getPluginManager().callEvent(event);
			if(event.isCancelled())
			{
				event.done();
				return;
			}
			reg(this);
			getEnabledRef().set(true);
			onEnable();
			event.done();
		}
	}
	default void disable()
	{
		synchronized(this)
		{
			if(!isEnabled())
				return;
			ModuleDisableEvent event=new ModuleDisableEvent(this);
			Bukkit.getPluginManager().callEvent(event);
			if(event.isCancelled())
			{
				event.done();
				return;
			}
			for(List<IModule> t: modules.values())
			{
				for(IModule m: new LinkedList<>(t))
				{
					if(m.isEnabled() && m.getDepends().contains(this))
						m.disable();
				}
			}
			for(Map.Entry<IRegistrar<?>,List<Object>> e: getRegisteredObjects().entrySet())
			{
				for(Object o: e.getValue())
				{
					e.getKey().unregister(TypeUtil.cast(o));
				}
			}
			getRegisteredObjects().clear();
			
			getEnabledRef().set(false);
			onDisable();
			event.done();
		}
	}
	default boolean isLoaded()
	{
		synchronized(this)
		{
			List<IModule> l=modules.get(this.getPlugin());
			if(l!=null)
			{
				return l.contains(this);
			}
			return false;
		}
	}
	default void load0()
	{
		synchronized(this)
		{
			if(isLoaded())
				return;
			for(IModule depend:this.getDepends())
			{
				depend.load();
			}
			List<IModule> ms=modules.get(this.getPlugin());
			if(ms==null)
			{
				ms=new LinkedList<>();
				modules.put(this.getPlugin(),ms);
			}
			ms.add(this);
		}
	}
	default void load()
	{
		synchronized(this)
		{
			load0();
			try
			{
				this.enable();
			}
			catch(Throwable e)
			{
				this.getPlugin().getLogger().warning(this.getPlugin().getName()+" has a exception on enabling module "+this.getClass().getName());
				e.printStackTrace();
			}
		}
	}
	default void unload()
	{
		synchronized(this)
		{
			List<IModule> ms=modules.get(this.getPlugin());
			if(ms==null || !isLoaded())
				return;
			this.disable();
			ms.remove(this);
			if(ms.isEmpty())
				modules.remove(this.getPlugin());
		}
	}
	static <T extends IModule> T enable(Class<T> interfase)
	{
		synchronized(IModule.class)
		{
			if(!interfase.isInterface())
				throw new IllegalArgumentException("Arg interfase must be a interface");
			T r=ClassUtil.newInstance(interfase);
			r.enable();
			return r;
		}
	}
	static void unloadModules(Plugin plugin)
	{
		synchronized(IModule.class)
		{
			List<IModule> ms=modules.get(plugin);
			if(ms!=null&&ms.size()>0)
			{
				for(IModule m:new LinkedList<>(ms))
				{
					m.unload();
				}
			}
		}
	}
	static void unloadAll()
	{
		synchronized(IModule.class)
		{
			Map<Plugin,List<IModule>> remaining=new HashMap<>();
			while(modules.size()>0)
			{
				Map.Entry<Plugin,List<IModule>> e=modules.entrySet().iterator().next();
				if(ListUtil.mergeLists(ListUtil.mergeLists(Lists.newArrayList(modules.keySet()),Lists.newArrayList(remaining.keySet())).stream().map(p->p.getDescription().getDepend().stream().map(d->Bukkit.getPluginManager().getPlugin(d)).collect(Collectors.toList())).toArray(List[]::new)).contains(e.getKey()))
				{
					remaining.put(e.getKey(),e.getValue());
					modules.remove(e.getKey());
				}
				else
				{
					unloadModules(e.getKey());
				}
			}
			if(!remaining.isEmpty())
			{
				modules.putAll(remaining);
				unloadAll();
			}
		}
	}
	
	class ModuleModule extends AbsModule
	{
		public static ModuleModule instance=new ModuleModule();
		public ModuleModule()
		{
			super(MzLib.instance);
		}
		
		@EventHandler
		void onPluginDisable(PluginDisableEvent event)
		{
			if(event.getPlugin()==MzLib.instance)
				IModule.unloadAll();
			else
				IModule.unloadModules(event.getPlugin());
		}
	}
}

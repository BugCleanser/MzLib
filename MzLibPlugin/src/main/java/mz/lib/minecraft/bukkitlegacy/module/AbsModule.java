package mz.lib.minecraft.bukkitlegacy.module;

import com.google.common.collect.*;
import mz.lib.Ref;
import mz.lib.minecraft.bukkitlegacy.ListenerRegistrar;
import org.bukkit.plugin.Plugin;

import java.util.*;

public abstract class AbsModule extends Ref<Boolean> implements IModule
{
	public Plugin plugin;
	public Set<IModule> depends;
	public Map<IRegistrar<?>,List<Object>> registeredObjects=new HashMap<>();
	
	public AbsModule(Plugin plugin,IModule ...depends)
	{
		super(false);
		this.plugin=plugin;
		this.depends=Sets.newHashSet(depends);
		if(this.getClass()!=ListenerRegistrar.class&&this.getClass()!=RegistrarRegistrar.class)
			this.depends.add(ListenerRegistrar.instance);
	}
	
	@Override
	public Plugin getPlugin()
	{
		return plugin;
	}
	
	@Override
	public Set<IModule> getDepends()
	{
		return depends;
	}
	
	@Override
	public Ref<Boolean> getEnabledRef()
	{
		return this;
	}
	
	@Override
	public Map<IRegistrar<?>,List<Object>> getRegisteredObjects()
	{
		return registeredObjects;
	}
	
	@Override
	public boolean equals(Object o)
	{
		return o==this;
	}
}

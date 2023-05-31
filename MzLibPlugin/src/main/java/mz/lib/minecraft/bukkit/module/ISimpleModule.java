package mz.lib.minecraft.bukkit.module;

import org.bukkit.plugin.Plugin;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ISimpleModule extends IModule
{
	@Override
	ModuleData getEnabledRef();
	
	@Override
	default Plugin getPlugin()
	{
		return getEnabledRef().getPlugin();
	}
	
	@Override
	default Set<IModule> getDepends()
	{
		return getEnabledRef().getDepends();
	}
	
	@Override
	default Map<IRegistrar<?>,List<Object>> getRegisteredObjects()
	{
		return getEnabledRef().getRegisteredObjects();
	}
}

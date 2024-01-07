package mz.mzlib.plugin;

import java.util.HashMap;
import java.util.Map;

public class PluginManager
{
	public Map<String,Plugin> plugins;
	public Map<Plugin,Boolean> pluginStates=new HashMap<>();
	
	public void loadPlugin(Plugin plugin)
	{
		String name=plugin.getName();
		if(plugins.containsKey(name))
			throw new IllegalArgumentException();
		plugins.put(name,plugin);
		plugin.onLoad();
		enablePlugin(plugin);
	}
	
	public void unloadPlugin(Plugin plugin)
	{
		disablePlugin(plugin);
		plugin.onUnload();
		pluginStates.remove(plugin);
		plugins.remove(plugin.getName(),plugin);
	}
	public void unloadPlugin(String name)
	{
		unloadPlugin(plugins.get(name));
	}
	
	public void enablePlugin(Plugin plugin)
	{
		if(pluginStates.put(plugin,true)!=Boolean.TRUE)
			plugin.onEnable();
	}
	public void disablePlugin(Plugin plugin)
	{
		if(pluginStates.put(plugin,false)!=Boolean.FALSE)
			plugin.onDisable();
	}
}

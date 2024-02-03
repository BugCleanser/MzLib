package mz.mzlib.plugin;

import mz.mzlib.MzLib;
import mz.mzlib.util.UnionClassLoader;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

public class PluginManager
{
	public static PluginManager instance=new PluginManager();
	
	public File pluginsDir=new File("./plugins");
	
	public UnionClassLoader unionClassLoader=new UnionClassLoader();
	public Map<String,Plugin> plugins=new HashMap<>();
	
	public void loadPlugin(Plugin plugin)
	{
		String name=plugin.getName();
		if(plugins.containsKey(name))
			throw new IllegalArgumentException();
		plugins.put(name,plugin);
		MzLib.instance.register(plugin.getModule());
	}
	public void unloadPlugin(Plugin plugin)
	{
		MzLib.instance.unregister(plugin.getModule());
		plugins.remove(plugin.getName(),plugin);
	}
	public Plugin getPlugin(String name)
	{
		return plugins.get(name);
	}
	public void unloadPlugin(String name)
	{
		unloadPlugin(getPlugin(name));
	}
	public Set<Plugin> loadingPlugins;
	public void registerPlugin(Plugin plugin)
	{
		if(loadingPlugins==null)
			loadPlugin(plugin);
		else
			loadingPlugins.add(plugin);
	}
	public boolean checkDepends(Plugin plugin)
	{
		for(String i:plugin.getDepends())
		{
			if(getPlugin(i)==null)
				return false;
		}
		return true;
	}
	public static List<Plugin> topologicalSort(Set<Plugin> plugins)
	{
		Map<String,Plugin> pluginMap=new HashMap<>();
		Map<String,Set<Plugin>> extensions=new HashMap<>();
		for(Plugin p:plugins)
		{
			pluginMap.put(p.getName(),p);
			extensions.put(p.getName(),new HashSet<>());
		}
		for(Plugin p:plugins)
		{
			HashSet<String> depends=new HashSet<>(p.getDepends());
			depends.addAll(p.getSoftDepends());
			for(String d:depends)
			{
				Set<Plugin> s=extensions.get(d);
				if(s!=null)
					s.add(p);
			}
		}
		Set<Plugin> visited=new HashSet<>();
		List<Plugin> result=new ArrayList<>();
		for(Plugin p:plugins)
		{
			if(extensions.get(p.getName()).isEmpty())
				topologicalSortDfs(p,pluginMap,extensions,visited,result,new HashSet<>());
		}
		if(result.size()!=plugins.size())
		{
			plugins=new HashSet<>(plugins);
			result.forEach(plugins::remove);
			throw new RuntimeException("Circular dependency detected involving plugin: "+plugins);
		}
		return result;
	}
	public static void topologicalSortDfs(Plugin now,Map<String,Plugin> pluginMap,Map<String,Set<Plugin>> extensions,Set<Plugin> visited,List<Plugin> result,Set<String> visiting)
	{
		if(!visited.add(now))
			return;
		if(!visiting.add(now.getName()))
			throw new RuntimeException("Circular dependency detected involving plugin: "+now.getName());
		try
		{
			boolean ready=true;
			HashSet<String> depends=new HashSet<>(now.getDepends());
			depends.addAll(now.getSoftDepends());
			for(String d:depends)
				if(pluginMap.containsKey(d)&&!visited.contains(pluginMap.get(d)))
				{
					ready=false;
					break;
				}
			if(ready)
				result.add(now);
			for(Plugin p:extensions.get(now.getName()))
				topologicalSortDfs(p,pluginMap,extensions,visited,result,visiting);
		}
		finally
		{
			visiting.remove(now.getName());
		}
	}
	public void loadPlugins(String[] args)
	{
		assert pluginsDir.isDirectory() || pluginsDir.mkdirs();
		for(File i: Objects.requireNonNull(pluginsDir.listFiles()))
		{
			try
			{
				URLClassLoader cl=new URLClassLoader(new URL[]{i.toURI().toURL()},unionClassLoader);
				try(JarFile jar=new JarFile(i))
				{
					Class.forName(jar.getManifest().getMainAttributes().getValue("Class-Path"),false,cl).getMethod("main", String[].class).invoke(null, (Object) args);
				}
				unionClassLoader.addMember(cl);
			}
			catch(Throwable e)
			{
				e.printStackTrace(System.err);
			}
		}
		for(Plugin i:topologicalSort(loadingPlugins))
		{
			if(checkDepends(i))
				loadPlugin(i);
			else
				System.err.println("Cannot load plugin "+i.name+": depend absent "+i.getDepends().stream().filter(d->getPlugin(d)==null).collect(Collectors.toList()));
		}
		loadingPlugins=null;
	}
	public static void main(String[] args)
	{
		MzLib.instance.load();
		instance.loadPlugins(args);
	}
}

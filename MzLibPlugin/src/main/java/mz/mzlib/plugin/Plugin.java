package mz.mzlib.plugin;

import mz.mzlib.module.MzModule;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

public class Plugin
{
	String name;
	Supplier<MzModule> moduleGetter;
	Set<String> depends;
	Set<String> softDepends;
	
	public Plugin(String name,Supplier<MzModule> moduleGetter)
	{
		this.name=name;
		this.moduleGetter=moduleGetter;
		this.depends=new HashSet<>();
		this.softDepends=new HashSet<>();
	}
	public Plugin depends(String ...depends)
	{
		this.depends.addAll(Arrays.asList(depends));
		return this;
	}
	public Plugin softDepends(String ...softDepends)
	{
		this.softDepends.addAll(Arrays.asList(softDepends));
		return this;
	}
	
	public String getName()
	{
		return name;
	}
	public Set<String> getDepends()
	{
		return depends;
	}
	public Set<String> getSoftDepends()
	{
		return softDepends;
	}
	public MzModule getModule()
	{
		return this.moduleGetter.get();
	}
	public boolean isLoaded()
	{
		return PluginManager.instance.getPlugin(this.getName())==this;
	}
	
	@Override
	public String toString()
	{
		return name+"@"+Integer.toHexString(hashCode());
	}
}

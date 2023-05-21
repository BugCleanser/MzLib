package mz.mzlib.module;

import mz.mzlib.javautil.Instance;

import java.util.HashSet;

public class RootModule extends MzModule implements Instance
{
	public static RootModule instance=new RootModule();
	
	public void load()
	{
		if(this.isLoaded)
			throw new IllegalStateException("Try to load the root module but it has been loaded.");
		this.isLoaded=true;
		this.onLoad();
	}
	public void unload()
	{
		if(!this.isLoaded)
			throw new IllegalStateException("Try to unload the root module but it's not loaded.");
		this.isLoaded=false;
		for(MzModule i:new HashSet<>(this.submodules))
			this.unregister(i);
		for(Object i:new HashSet<>(this.registeredObjects.keySet()))
			this.unregister(i);
		this.onUnload();
	}
	
	@Override
	public void onLoad()
	{
		register(Instance.InstanceRegistrar.instance);
		register(RegistrarRegistrar.instance);
	}
	@Override
	public void onUnload()
	{
	}
}

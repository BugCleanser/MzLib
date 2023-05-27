package mz.mzlib;

import mz.mzlib.module.MzModule;
import mz.mzlib.module.RegistrarRegistrar;
import mz.mzlib.util.Instance;
import mz.mzlib.util.delegator.DefaultDelegatorClassAnalyzer;
import mz.mzlib.util.delegator.DelegatorClassAnalyzerRegistrar;

import java.util.HashSet;

public class MzLib extends MzModule implements Instance
{
	public static MzLib instance=new MzLib();
	
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
		InstanceRegistrar.instance.register(this,this);
		register(RegistrarRegistrar.instance);
		
		register(DelegatorClassAnalyzerRegistrar.instance);
		register(DefaultDelegatorClassAnalyzer.instance);
	}
	@Override
	public void onUnload()
	{
		InstanceRegistrar.instance.unregister(this,this);
	}
}

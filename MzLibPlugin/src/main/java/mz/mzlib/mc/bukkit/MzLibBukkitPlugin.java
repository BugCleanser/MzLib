package mz.mzlib.mc.bukkit;

import mz.mzlib.util.Instance;
import mz.mzlib.module.RootModule;
import org.bukkit.plugin.java.JavaPlugin;

public class MzLibBukkitPlugin extends JavaPlugin implements Instance
{
	public static MzLibBukkitPlugin instance;
	{
		instance=this;
	}
	
	@Override
	public void onEnable()
	{
		RootModule.instance.load();
		RootModule.instance.register(MzLibBukkit.instance);
	}
	
	@Override
	public void onDisable()
	{
		RootModule.instance.unload();
	}
}

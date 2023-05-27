package mz.mzlib.mc.bukkit;

import mz.mzlib.util.Instance;
import mz.mzlib.MzLib;
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
		MzLib.instance.load();
		MzLib.instance.register(MzLibBukkit.instance);
	}
	
	@Override
	public void onDisable()
	{
		MzLib.instance.unload();
	}
}

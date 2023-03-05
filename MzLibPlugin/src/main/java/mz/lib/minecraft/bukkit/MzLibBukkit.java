package mz.lib.minecraft.bukkit;

import org.bukkit.plugin.java.*;

public class MzLibBukkit extends JavaPlugin
{
	public static MzLibBukkit instance;
	{
		instance=this;
	}
	
	@Override
	public void onEnable()
	{
		MzLibBukkitModule.instance.load();
	}
	
	@Override
	public void onDisable()
	{
		MzLibBukkitModule.instance.unload();
	}
}

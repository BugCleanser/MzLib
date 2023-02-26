package mz.lib.minecraft.bukkit;

import org.bukkit.plugin.java.*;

public class MzLibBukkit extends JavaPlugin
{
	@Override
	public void onEnable()
	{
		MzLibBukkitModule.instance=new MzLibBukkitModule(this);
		MzLibBukkitModule.instance.load();
	}
	
	@Override
	public void onDisable()
	{
		MzLibBukkitModule.instance.unload();
	}
}

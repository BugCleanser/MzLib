package mz.mzlib.mc.bukkit;

import mz.mzlib.mc.MzLibMinecraft;
import mz.mzlib.mc.bukkit.item.ItemFactoryBukkit;
import mz.mzlib.mc.bukkit.item.ItemStackFactoryBukkit;
import mz.mzlib.module.MzModule;

public class MzLibBukkit extends MzModule
{
	public static MzLibBukkit instance=new MzLibBukkit();
	
	@Override
	public void onLoad()
	{
		register(MzLibMinecraft.instance);
		
		register(MinecraftServerBukkit.getInstance());
		register(MinecraftMainThreadRunnerBukkit.instance);
		register(ItemFactoryBukkit.instance);
		register(ItemStackFactoryBukkit.instance);
	}
}

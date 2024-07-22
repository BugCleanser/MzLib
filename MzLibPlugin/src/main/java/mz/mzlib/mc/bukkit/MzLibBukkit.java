package mz.mzlib.mc.bukkit;

import mz.mzlib.mc.MzLibMinecraft;
import mz.mzlib.mc.bukkit.item.ItemFactoryBukkit;
import mz.mzlib.mc.bukkit.item.ItemStackFactoryBukkit;
import mz.mzlib.module.MzModule;
import mz.mzlib.util.Instance;

public class MzLibBukkit extends MzModule implements Instance
{
	public static MzLibBukkit instance=new MzLibBukkit();
	
	@Override
	public void onLoad()
	{
		register(MzLibBukkitPlugin.instance);
		register(MzLibMinecraft.instance);
		register(ItemFactoryBukkit.instance);
		register(ItemStackFactoryBukkit.instance);
	}
}

package mz.mzlib.mc.bukkit;

import mz.mzlib.mc.bukkit.item.ItemFactoryImpl;
import mz.mzlib.mc.bukkit.item.ItemStackFactoryImpl;
import mz.mzlib.mc.item.ItemStackFactory;
import mz.mzlib.util.Instance;
import mz.mzlib.mc.MzLibMinecraft;
import mz.mzlib.module.MzModule;

public class MzLibBukkit extends MzModule implements Instance
{
	public static MzLibBukkit instance=new MzLibBukkit();
	
	@Override
	public void onLoad()
	{
		register(MzLibBukkitPlugin.instance);
		register(MzLibMinecraft.instance);
		register(ItemFactoryImpl.instance);
		register(ItemStackFactoryImpl.instance);
	}
}

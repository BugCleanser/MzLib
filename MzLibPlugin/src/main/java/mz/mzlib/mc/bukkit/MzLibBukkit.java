package mz.mzlib.mc.bukkit;

import mz.mzlib.mc.MzLibMinecraft;
import mz.mzlib.mc.bukkit.item.ItemFactoryImpl;
import mz.mzlib.mc.bukkit.item.ItemStackFactoryImpl;
import mz.mzlib.module.MzModule;
import mz.mzlib.util.Instance;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;

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

package mz.mzlib.mc.bukkit;

import mz.mzlib.javautil.Instance;
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
	}
}

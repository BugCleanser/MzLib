package mz.lib.minecraft.bukkit;

import mz.lib.minecraft.*;
import mz.lib.minecraft.bukkit.permission.*;
import mz.lib.minecraft.bukkit.task.*;
import mz.lib.module.*;
import mz.module.*;
import mz.mzlib.*;
import mz.lib.minecraft.Server;
import mz.mzlib.bukkit.permission.*;
import mz.mzlib.bukkit.task.*;
import org.bukkit.*;

public class MzLibBukkitModule extends MzModule
{
	public static MzLibBukkitModule instance;
	public MzLibBukkit plugin;
	public MzLibBukkitModule(MzLibBukkit plugin)
	{
		this.plugin=plugin;
	}
	
	@Override
	public void onLoad()
	{
		Server.instance=new ServerImpl();
		Factory.instance=new FactoryImpl();
		
		reg(SyncTaskRegistrar.instance);
		reg(AsyncTaskRegistrar.instance);
		reg(SyncTimerTaskRegistrar.instance);
		reg(AsyncTimerTaskRegistrar.instance);
		reg(new Registrar<>(PermissionImpl.class,(m,p)->
		{
			Bukkit.getPluginManager().addPermission(p.delegation);
			return true;
		},(m,p)->Bukkit.getPluginManager().removePermission(p.delegation)));
	}
}

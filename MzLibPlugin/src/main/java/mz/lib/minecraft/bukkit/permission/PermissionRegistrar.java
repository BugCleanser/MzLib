package mz.lib.minecraft.bukkit.permission;

import mz.lib.minecraft.bukkit.MzLib;
import mz.lib.minecraft.bukkit.module.AbsModule;
import mz.lib.minecraft.bukkit.module.IRegistrar;
import mz.lib.minecraft.bukkit.module.RegistrarRegistrar;
import org.bukkit.Bukkit;
import org.bukkit.permissions.Permission;

public class PermissionRegistrar extends AbsModule implements IRegistrar<Permission>
{
	public static PermissionRegistrar instance=new PermissionRegistrar();
	public PermissionRegistrar()
	{
		super(MzLib.instance,RegistrarRegistrar.instance);
	}
	
	@Override
	public Class<Permission> getType()
	{
		return Permission.class;
	}
	@Override
	public boolean register(Permission obj)
	{
		Bukkit.getPluginManager().addPermission(obj);
		return true;
	}
	@Override
	public void unregister(Permission obj)
	{
		Bukkit.getPluginManager().removePermission(obj);
	}
}

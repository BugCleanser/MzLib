package mz.lib.minecraft.bukkit.permission;

import mz.lib.minecraft.permission.*;
import mz.lib.minecraft.permission.*;

public class PermissionDefaultImpl implements PermissionDefault
{
	public org.bukkit.permissions.PermissionDefault delegation;
	public PermissionDefaultImpl(org.bukkit.permissions.PermissionDefault delegation)
	{
		this.delegation=delegation;
	}
	
	public org.bukkit.permissions.PermissionDefault getDelegation()
	{
		return delegation;
	}
}

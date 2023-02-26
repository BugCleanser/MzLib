package mz.lib.minecraft.bukkit.permission;

import mz.lib.minecraft.permission.*;
import mz.mzlib.permission.*;

import java.util.*;

public class PermissionImpl implements Permission
{
	public org.bukkit.permissions.Permission delegation;
	public PermissionImpl(org.bukkit.permissions.Permission delegation)
	{
		this.delegation=delegation;
	}
	
	@Override
	public String getName()
	{
		return delegation.getName();
	}
	
	@Override
	public Map<String,Boolean> getChildren()
	{
		return delegation.getChildren();
	}
	
	@Override
	public PermissionDefaultImpl getDefault()
	{
		return new PermissionDefaultImpl(delegation.getDefault());
	}
	
	@Override
	public String getDescription()
	{
		return delegation.getDescription();
	}
}

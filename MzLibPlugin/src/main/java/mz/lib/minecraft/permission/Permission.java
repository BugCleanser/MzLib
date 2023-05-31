package mz.lib.minecraft.permission;

import mz.lib.minecraft.*;
import mz.mzlib.*;

import java.util.*;

public interface Permission
{
	static Permission newInstance(String name,String description,PermissionDefault defaultValue,Map<String,Boolean> children)
	{
		return Factory.instance.newPermission(name,description,defaultValue,children);
	}
	static Permission newInstance(String name,PermissionDefault defaultValue)
	{
		return newInstance(name,null,defaultValue,null);
	}
	static Permission newInstance(String name)
	{
		return newInstance(name,null);
	}
	
	String getName();
	Map<String,Boolean> getChildren();
	PermissionDefault getDefault();
	String getDescription();
}

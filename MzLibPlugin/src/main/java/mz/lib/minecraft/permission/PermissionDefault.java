package mz.lib.minecraft.permission;

import mz.lib.minecraft.*;

public interface PermissionDefault
{
	PermissionDefault TRUE=Factory.instance.permissionDefaultTrue();
	PermissionDefault FALSE=Factory.instance.permissionDefaultFalse();
	PermissionDefault OP=Factory.instance.permissionDefaultOp();
	PermissionDefault NOT_OP=Factory.instance.permissionDefaultNotOp();
}

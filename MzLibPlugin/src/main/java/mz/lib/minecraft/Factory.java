package mz.lib.minecraft;

import mz.lib.minecraft.item.*;
import mz.lib.minecraft.nbt.*;
import mz.lib.minecraft.permission.*;
import mz.mzlib.item.*;
import mz.mzlib.nbt.*;
import mz.mzlib.permission.*;

import java.util.*;

public abstract class Factory
{
	public static Factory instance;
	
	public abstract Identifier newIdentifier(String string);
	public abstract Identifier newIdentifier(String namespace,String key);
	
	public abstract NbtObject newNbtObject();
	
	public abstract NbtPrimitive newNbtPrimitive(Object value);
	
	public abstract NbtList newNbtList();
	
	public abstract NbtByteArray newNbtByteArray();
	
	public abstract NbtIntArray newNbtIntArray();
	public abstract Item getItem(Identifier id);
	public abstract ItemStack newItemStack(Item item,int amount);
	public abstract Permission newPermission(String name,String description,PermissionDefault defaultValue,Map<String,Boolean> children);
	
	public abstract PermissionDefault permissionDefaultTrue();
	public abstract PermissionDefault permissionDefaultFalse();
	public abstract PermissionDefault permissionDefaultOp();
	public abstract PermissionDefault permissionDefaultNotOp();
}

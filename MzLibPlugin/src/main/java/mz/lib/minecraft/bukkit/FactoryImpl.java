package mz.lib.minecraft.bukkit;

import mz.lib.minecraft.*;
import mz.lib.minecraft.bukkit.nms.*;
import mz.lib.minecraft.bukkit.permission.*;
import mz.lib.minecraft.item.*;
import mz.lib.minecraft.nbt.*;
import mz.lib.minecraft.permission.*;
import mz.mzlib.*;
import mz.mzlib.bukkit.nms.*;
import mz.mzlib.bukkit.permission.*;
import mz.mzlib.item.*;
import mz.mzlib.nbt.*;
import mz.mzlib.permission.*;

import java.util.*;

public class FactoryImpl extends Factory
{
	@Override
	public Identifier newIdentifier(String string)
	{
		return NmsMinecraftKey.newInstance(string);
	}
	@Override
	public Identifier newIdentifier(String namespace,String key)
	{
		return NmsMinecraftKey.newInstance(namespace,key);
	}
	
	@Override
	public NbtObject newNbtObject()
	{
		return NmsNBTTagCompound.newInstance();
	}
	
	@Override
	public NbtPrimitive newNbtPrimitive(Object value)
	{
		if(value instanceof Byte)
			return NmsNBTTagByte.newInstance((Byte)value);
		if(value instanceof Short)
			return NmsNBTTagShort.newInstance((Short)value);
		if(value instanceof Integer)
			return NmsNBTTagInt.newInstance((Integer)value);
		if(value instanceof Long)
			return NmsNBTTagLong.newInstance((Long)value);
		if(value instanceof Float)
			return NmsNBTTagFloat.newInstance((Float)value);
		throw new UnsupportedOperationException("Unsupported primitive nbt: "+value+".");
	}
	
	@Override
	public NbtList newNbtList()
	{
		return NmsNBTTagList.newInstance();
	}
	
	@Override
	public NbtByteArray newNbtByteArray()
	{
		return NbtByteArray.newInstance();
	}
	
	@Override
	public NbtIntArray newNbtIntArray()
	{
		return NbtIntArray.newInstance();
	}
	
	@Override
	public Item getItem(Identifier id)
	{
		return NmsItem.fromId((NmsMinecraftKey)id);
	}
	
	@Override
	public ItemStack newItemStack(Item item,int amount)
	{
		return NmsItemStack.newInstance((NmsItem)item,amount);
	}
	
	@Override
	public Permission newPermission(String name,String description,PermissionDefault defaultValue,Map<String,Boolean> children)
	{
		return new PermissionImpl(new org.bukkit.permissions.Permission(name,description,defaultValue==null?null:((PermissionDefaultImpl)defaultValue).getDelegation(),children));
	}
	@Override
	public PermissionDefault permissionDefaultTrue()
	{
		return PermissionDefaultImpl.TRUE;
	}
	@Override
	public PermissionDefault permissionDefaultFalse()
	{
		return PermissionDefaultImpl.FALSE;
	}
	@Override
	public PermissionDefault permissionDefaultOp()
	{
		return PermissionDefaultImpl.OP;
	}
	@Override
	public PermissionDefault permissionDefaultNotOp()
	{
		return PermissionDefaultImpl.NOT_OP;
	}
}

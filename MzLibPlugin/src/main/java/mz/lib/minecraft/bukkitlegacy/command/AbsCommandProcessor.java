package mz.lib.minecraft.bukkitlegacy.command;

import org.bukkit.permissions.Permission;

public abstract class AbsCommandProcessor implements ICommandProcessor
{
	public String[] names;
	public boolean mustOp;
	public Permission permission;
	public AbsCommandProcessor(boolean mustOp,Permission permission,String... names)
	{
		setMustOp(mustOp);
		this.permission=permission;
		this.names=names;
	}
	
	public String[] getNames()
	{
		return names;
	}
	public void setNames(String[] names)
	{
		this.names=names;
	}
	public boolean matchName(String name)
	{
		for(String n: names)
		{
			if(n.equalsIgnoreCase(name))
				return true;
		}
		return false;
	}
	@Override
	public boolean isMustOp()
	{
		return this.mustOp;
	}
	@Override
	public void setMustOp(boolean mustOp)
	{
		this.mustOp=mustOp;
	}
	@Override
	public Permission getPermission()
	{
		return permission;
	}
	@Override
	public void setPermission(Permission permission)
	{
		this.permission=permission;
	}
}

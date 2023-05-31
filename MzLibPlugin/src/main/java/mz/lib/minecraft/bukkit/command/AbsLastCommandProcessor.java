package mz.lib.minecraft.bukkit.command;

import org.bukkit.permissions.Permission;

import java.util.List;

public abstract class AbsLastCommandProcessor extends AbsCommandProcessor implements ILastCommandProcessor
{
	private final List<FinalCommandExecutor> executors;
	
	public AbsLastCommandProcessor(boolean mustOp,Permission permission,String... names)
	{
		super(mustOp,permission,names);
		this.executors=FinalCommandExecutor.getCommandExecutors(this);
	}
	
	@Override
	public List<FinalCommandExecutor> getExecutors()
	{
		return executors;
	}
}

package mz.lib.minecraft.bukkit.command.argparser;

public abstract class AbsArgParser<T> implements IArgParser<T>
{
	Class<T> type;
	
	public AbsArgParser(Class<T> type)
	{
		this.type=type;
	}
	
	@Override
	public Class<T> getType()
	{
		return type;
	}
}

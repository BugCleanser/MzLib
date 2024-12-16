package mz.mzlib.minecraft.command;

import java.util.*;

public class CommandBuilder
{
	public String name;
	public List<String> aliases;
	public CommandBuilder(String name, String ...aliases)
	{
		this.name=name;
		this.aliases=new ArrayList<>(Arrays.asList(aliases));
	}
	public Map<String, Command> children=new HashMap<>();
	public List<CommandExecutor> executor=new ArrayList<>();
	public CommandBuilder addChild(Command child)
	{
		children.put(child.name, child);
		for(String alias:child.aliases)
		{
			children.put(alias, child);
		}
		return this;
	}
	public CommandBuilder addExecutor(CommandExecutor executor)
	{
		this.executor.add(executor);
		return this;
	}
	
	public Command build()
	{
		return new Command(this);
	}
}

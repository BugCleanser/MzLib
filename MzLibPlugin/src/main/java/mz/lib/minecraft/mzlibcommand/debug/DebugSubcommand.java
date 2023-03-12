package mz.lib.minecraft.mzlibcommand.debug;

import mz.lib.minecraft.command.AbsFrontCommandProcessor;
import org.bukkit.permissions.*;

public class DebugSubcommand extends AbsFrontCommandProcessor
{
	public static DebugSubcommand instance=new DebugSubcommand();
	public DebugSubcommand()
	{
		super(false,new Permission("mz.lib.command.debug",PermissionDefault.OP),"debug");
		
		this.subcommands.add(DebugSlotCommand.instance);
		this.subcommands.add(DebugFieldsSubcommand.instance);
		this.subcommands.add(DebugMethodsSubcommand.instance);
	}
}

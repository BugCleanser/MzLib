package mz.lib.minecraft.mzlibcommand;

import mz.lib.minecraft.command.AbsFrontCommandProcessor;
import mz.lib.minecraft.mzlibcommand.debug.DebugSubcommand;

public class MzLibCommand extends AbsFrontCommandProcessor
{
	public MzLibCommand()
	{
		super(false,null,"mzlib","mz");
		this.subcommands.add(ReloadSubcommand.instance);
		this.subcommands.add(DebugSubcommand.instance);
		this.subcommands.add(GiveSubcommand.instance);
		this.subcommands.add(RecipesSubcommand.instance);
		this.subcommands.add(RespawnSubcommand.instance);
		this.subcommands.add(ResetRecipesSubcommand.instance);
		this.subcommands.add(EnchantSubcommand.instance);
		this.subcommands.add(ItemInfoSubcommand.instance);
		this.subcommands.add(PlayerInfoSubcommand.instance);
		this.subcommands.add(LoadMapSubcommand.instance);
		this.subcommands.add(SkullSubcommand.instance);
	}
}

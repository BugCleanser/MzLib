package mz.lib.minecraft.mzlibcommand;

import mz.lib.minecraft.MinecraftLanguages;
import mz.lib.minecraft.bukkitlegacy.MzLib;
import mz.lib.minecraft.command.AbsLastCommandProcessor;
import mz.lib.minecraft.command.CommandHandler;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.*;

public class ResetRecipesSubcommand extends AbsLastCommandProcessor
{
	public static ResetRecipesSubcommand instance=new ResetRecipesSubcommand();
	public ResetRecipesSubcommand()
	{
		super(false,new Permission("mz.lib.command.resetRecipes",PermissionDefault.OP),"resetRecipes");
	}
	
	@Override
	public String getEffect(CommandSender sender)
	{
		return MinecraftLanguages.get(sender,"mzlib.command.resetRecipes.effect");
	}
	
	@CommandHandler
	public void execute(CommandSender sender)
	{
		Bukkit.resetRecipes();
		MzLib.sendPluginMessage(sender,MzLib.instance,MinecraftLanguages.get(sender,"mzlib.command.resetRecipes.success"));
	}
}

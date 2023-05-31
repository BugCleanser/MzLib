package mz.lib.minecraft.bukkit.mzlibcommand;

import mz.lib.minecraft.bukkit.LangUtil;
import mz.lib.minecraft.bukkit.MzLib;
import mz.lib.minecraft.bukkit.command.AbsLastCommandProcessor;
import mz.lib.minecraft.bukkit.command.CommandHandler;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

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
		return LangUtil.getTranslated(sender,"mzlib.command.resetRecipes.effect");
	}
	
	@CommandHandler
	public void execute(CommandSender sender)
	{
		Bukkit.resetRecipes();
		MzLib.sendPluginMessage(sender,MzLib.instance,LangUtil.getTranslated(sender,"mzlib.command.resetRecipes.success"));
	}
}

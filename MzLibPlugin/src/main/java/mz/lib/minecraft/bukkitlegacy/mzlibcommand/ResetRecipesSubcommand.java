package mz.lib.minecraft.bukkitlegacy.mzlibcommand;

import mz.lib.minecraft.bukkitlegacy.LangUtil;
import mz.lib.minecraft.bukkitlegacy.MzLib;
import mz.lib.minecraft.bukkitlegacy.command.AbsLastCommandProcessor;
import mz.lib.minecraft.bukkitlegacy.command.CommandHandler;
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
		return LangUtil.getTranslated(sender,"mzlib.command.resetRecipes.effect");
	}
	
	@CommandHandler
	public void execute(CommandSender sender)
	{
		Bukkit.resetRecipes();
		MzLib.sendPluginMessage(sender,MzLib.instance,LangUtil.getTranslated(sender,"mzlib.command.resetRecipes.success"));
	}
}

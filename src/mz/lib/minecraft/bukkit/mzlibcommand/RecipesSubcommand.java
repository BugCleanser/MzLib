package mz.lib.minecraft.bukkit.mzlibcommand;

import mz.lib.minecraft.bukkit.LangUtil;
import mz.lib.minecraft.bukkit.command.AbsLastCommandProcessor;
import mz.lib.minecraft.bukkit.command.CommandHandler;
import mz.lib.minecraft.bukkit.gui.*;
import mz.lib.minecraft.bukkit.recipe.*;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.*;

public class RecipesSubcommand extends AbsLastCommandProcessor
{
	public static RecipesSubcommand instance=new RecipesSubcommand();
	public RecipesSubcommand()
	{
		super(false,new Permission("mz.lib.command.recipes",PermissionDefault.OP),"recipes");
	}
	
	@Override
	public String getEffect(CommandSender sender)
	{
		return LangUtil.getTranslated(sender,"mzlib.command.recipes.effect");
	}
	
	@CommandHandler
	public void execute(Player sender)
	{
		ViewList.get(sender).start(RecipeTypeSelector.instance);
	}
}

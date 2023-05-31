package mz.lib.minecraft.mzlibcommand;

import mz.lib.minecraft.MinecraftLanguages;
import mz.lib.minecraft.command.AbsLastCommandProcessor;
import mz.lib.minecraft.command.CommandHandler;
import mz.lib.minecraft.bukkitlegacy.gui.*;
import mz.lib.minecraft.bukkitlegacy.recipe.*;
import mz.lib.minecraft.recipe.*;
import mz.lib.minecraft.ui.*;
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
		return MinecraftLanguages.get(sender,"mzlib.command.recipes.effect");
	}
	
	@CommandHandler
	public void execute(Player sender)
	{
		UIStack.get(sender).start(RecipeTypeSelector.instance);
	}
}

package mz.lib.minecraft.bukkit.mzlibcommand;

import mz.lib.minecraft.bukkit.LangUtil;
import mz.lib.minecraft.bukkit.MzLib;
import mz.lib.minecraft.bukkit.command.AbsLastCommandProcessor;
import mz.lib.minecraft.bukkit.command.CommandHandler;
import mz.lib.minecraft.bukkit.command.argparser.ArgInfo;
import mz.lib.minecraft.bukkit.itemstack.ItemStackBuilder;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

public class EnchantSubcommand extends AbsLastCommandProcessor
{
	public static EnchantSubcommand instance=new EnchantSubcommand();
	public EnchantSubcommand()
	{
		super(false,new Permission("mz.lib.command.enchant",PermissionDefault.OP),"enchant","enchantment");
	}
	
	@CommandHandler
	public void execute(Player sender,Enchantment enchant)
	{
		execute(sender,enchant,(short) 1);
	}
	@CommandHandler
	public void execute(Player sender,Enchantment enchant,@ArgInfo(name="mzlib.command.enchant.level",presets={"1","3","5","32767"}) short level)
	{
		ItemStack is=sender.getInventory().getItemInMainHand();
		if(!ItemStackBuilder.isAir(is))
		{
			if(level!=0)
				is.addUnsafeEnchantment(enchant,level);
			else
				is.removeEnchantment(enchant);
			sender.getInventory().setItemInMainHand(is);
		}
		else
			MzLib.sendPluginMessage(sender,MzLib.instance,LangUtil.getTranslated(sender,"mzlib.command.enchant.error"));
	}
	
	@Override
	public String getEffect(CommandSender sender)
	{
		return LangUtil.getTranslated(sender,"mzlib.command.enchant.effect");
	}
}

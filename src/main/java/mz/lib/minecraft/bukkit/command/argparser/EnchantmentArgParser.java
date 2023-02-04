package mz.lib.minecraft.bukkit.command.argparser;

import mz.lib.StringUtil;
import mz.lib.minecraft.bukkit.EnchantUtil;
import mz.lib.minecraft.bukkit.LangUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;

import java.util.ArrayList;
import java.util.List;

public class EnchantmentArgParser extends AbsArgParser<Enchantment>
{
	public EnchantmentArgParser()
	{
		super(Enchantment.class);
	}
	
	@Override
	public Enchantment parse(CommandSender sender,String arg)
	{
		for(Enchantment e: Enchantment.values())
		{
			if(LangUtil.getTranslated(sender,EnchantUtil.getTranslateKey(EnchantUtil.getEnchantKey(e))).equals(arg))
				return e;
		}
		return null;
	}
	
	@Override
	public String getTypeName(CommandSender player,double max,double min)
	{
		return LangUtil.getTranslated(player,"mzlib.command.default.type.enchantName");
	}
	
	@Override
	public List<String> getDefaultPreset(CommandSender player,double max,double min)
	{
		List<String> r=new ArrayList<>();
		for(Enchantment e: Enchantment.values())
		{
			r.add(LangUtil.getTranslated(player,EnchantUtil.getTranslateKey(EnchantUtil.getEnchantKey(e))));
		}
		return r;
	}
	
	@Override
	public boolean checkFront(CommandSender sender,String arg,double min,double max)
	{
		for(Enchantment e: Enchantment.values())
		{
			if(StringUtil.startsWithIgnoreCase(LangUtil.getTranslated(sender,EnchantUtil.getTranslateKey(EnchantUtil.getEnchantKey(e))),arg))
				return true;
		}
		return false;
	}
}

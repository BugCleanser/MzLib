package mz.lib.minecraft.bukkit.mzlibcommand;

import mz.lib.minecraft.bukkit.command.*;
import mz.lib.minecraft.bukkit.entity.*;
import mz.lib.minecraft.bukkit.itemstack.*;
import org.bukkit.entity.*;
import org.bukkit.permissions.*;

public class SkullSubcommand extends AbsLastCommandProcessor
{
	public static SkullSubcommand instance=new SkullSubcommand();
	
	public SkullSubcommand()
	{
		super(false,new Permission("mz.lib.command.skull",PermissionDefault.OP),"skull");
	}
	
	@CommandHandler
	public void execute(Player sender,String url)
	{
		PlayerUtil.give(sender,ItemStackBuilder.newSkull(null,url).get());
	}
}

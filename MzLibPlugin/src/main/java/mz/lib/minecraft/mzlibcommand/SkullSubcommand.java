package mz.lib.minecraft.mzlibcommand;

import mz.lib.minecraft.bukkitlegacy.command.*;
import mz.lib.minecraft.bukkitlegacy.itemstack.*;
import mz.lib.minecraft.command.*;
import mz.lib.minecraft.entity.*;
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

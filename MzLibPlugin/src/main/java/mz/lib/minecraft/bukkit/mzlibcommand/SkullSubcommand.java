package mz.lib.minecraft.bukkit.mzlibcommand;

import mz.lib.minecraft.bukkit.command.AbsLastCommandProcessor;
import mz.lib.minecraft.bukkit.command.CommandHandler;
import mz.lib.minecraft.bukkit.entity.PlayerUtil;
import mz.lib.minecraft.bukkit.itemstack.ItemStackBuilder;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

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

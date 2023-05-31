package mz.lib.minecraft.bukkit.mzlibcommand;

import mz.lib.minecraft.bukkit.LangUtil;
import mz.lib.minecraft.bukkit.MzLib;
import mz.lib.minecraft.bukkit.command.AbsLastCommandProcessor;
import mz.lib.minecraft.bukkit.command.CommandHandler;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.*;

public class ReloadSubcommand extends AbsLastCommandProcessor
{
	public static ReloadSubcommand instance=new ReloadSubcommand();
	public ReloadSubcommand()
	{
		super(false,new Permission("mz.lib.command.reload",PermissionDefault.OP),"reload");
	}
	
	@Override
	public String getEffect(CommandSender sender)
	{
		return LangUtil.getTranslated(sender,"mzlib.command.reload.effect");
	}
	
	@CommandHandler
	public void execute(CommandSender sender)
	{
		MzLib.instance.reloadConfig();
		MzLib.sendPluginMessage(sender,MzLib.instance,LangUtil.getTranslated(sender,"mzlib.command.reload.success"));
	}
}

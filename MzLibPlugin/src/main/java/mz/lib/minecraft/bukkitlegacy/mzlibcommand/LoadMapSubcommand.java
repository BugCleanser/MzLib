package mz.lib.minecraft.bukkitlegacy.mzlibcommand;

import mz.lib.*;
import mz.lib.minecraft.MinecraftLanguages;
import mz.lib.minecraft.bukkitlegacy.MzLib;
import mz.lib.minecraft.command.AbsLastCommandProcessor;
import mz.lib.minecraft.command.CommandHandler;
import mz.lib.minecraft.command.argparser.ArgInfo;
import mz.lib.minecraft.bukkitlegacy.entity.PlayerUtil;
import mz.lib.minecraft.bukkitlegacy.item.map.*;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.*;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.*;

public class LoadMapSubcommand extends AbsLastCommandProcessor
{
	public static LoadMapSubcommand instance=new LoadMapSubcommand();
	public LoadMapSubcommand()
	{
		super(false,new Permission("mz.lib.command.loadMap",PermissionDefault.OP),"loadMap");
	}
	
	@Override
	public String getEffect(CommandSender sender)
	{
		return MinecraftLanguages.translate(sender,"mzlib.command.loadMap.effect");
	}
	
	@CommandHandler
	public void execute(Player sender,@ArgInfo(name="mzlib.command.loadMap.x",presets={"1","2","3","4"},min=1) int x,@ArgInfo(name="mzlib.command.loadMap.y",presets={"1","2","3","4"},min=1) int y,@ArgInfo(name="mzlib.command.loadMap.file") String file)
	{
		try
		{
			BufferedImage im;
			if(StringUtil.startsWithIgnoreCase(file,"http://")||StringUtil.startsWithIgnoreCase(file,"https://"))
				im=ImageIO.read(new URL(file));
			else
				im=ImageIO.read(new File(file));
			
			for(BitMap i:BitMap.fromImage(im,x,y))
				PlayerUtil.give(sender,i.getItemStack().getRaw());
		}
		catch(IIOException e)
		{
			MzLib.instance.sendPluginMessage(sender,e.getMessage());
		}
		catch(Throwable e)
		{
			throw TypeUtil.throwException(e);
		}
	}
}

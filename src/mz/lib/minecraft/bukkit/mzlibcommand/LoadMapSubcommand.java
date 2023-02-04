package mz.lib.minecraft.bukkit.mzlibcommand;

import mz.lib.*;
import mz.lib.minecraft.bukkit.LangUtil;
import mz.lib.minecraft.bukkit.MzLib;
import mz.lib.minecraft.bukkit.command.AbsLastCommandProcessor;
import mz.lib.minecraft.bukkit.command.CommandHandler;
import mz.lib.minecraft.bukkit.command.argparser.ArgInfo;
import mz.lib.minecraft.bukkit.entity.PlayerUtil;
import mz.lib.minecraft.bukkit.item.map.BitMap;
import mz.lib.minecraft.bukkit.item.map.MzMapCanvas;
import mz.lib.mzlang.MzObject;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.*;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import java.awt.*;
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
		return LangUtil.getTranslated(sender,"mzlib.command.loadMap.effect");
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
			BufferedImage tar=new BufferedImage(x*128,y*128,im.getType());
			
			Graphics2D g=tar.createGraphics();
			try
			{
				g.drawImage(im.getScaledInstance(x*128,y*128,Image.SCALE_SMOOTH),0,0,x*128,y*128,null);
				for(int j=0;j!=y;j++)
					for(int i=0;i!=x;i++)
					{
						BufferedImage s=tar.getSubimage(i*128,j*128,128,128);
						BitMap item=MzObject.newInstance(BitMap.class);
						MzMapCanvas canvas=new MzMapCanvas(128,128);
						for(int k=0;k!=128;k++)
							for(int l=0;l!=128;l++)
								canvas.setPixel(k,l,new Color(s.getRGB(k,l),true));
						item.setBits(canvas.data);
						PlayerUtil.give(sender,item.getItemStack().getRaw());
					}
			}
			finally
			{
				g.dispose();
			}
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

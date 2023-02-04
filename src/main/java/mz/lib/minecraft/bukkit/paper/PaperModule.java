package mz.lib.minecraft.bukkit.paper;

import com.destroystokyo.paper.PaperConfig;
import mz.lib.minecraft.bukkit.MzLib;
import mz.lib.minecraft.bukkit.module.AbsModule;

public class PaperModule extends AbsModule
{
	public static PaperModule instance=new PaperModule();
	public PaperModule()
	{
		super(MzLib.instance);
	}
	
	public boolean isPaper()
	{
		try
		{
			new PaperConfig();
			return true;
		}
		catch(NoClassDefFoundError e)
		{
			return false;
		}
	}
	
	@Override
	public void onEnable()
	{
	}
}

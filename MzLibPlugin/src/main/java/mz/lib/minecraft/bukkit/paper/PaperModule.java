package mz.lib.minecraft.bukkit.paper;

import mz.lib.minecraft.bukkitlegacy.MzLib;
import mz.lib.module.MzModule;
import mz.lib.wrapper.WrappedObject;

public class PaperModule extends MzModule
{
	public static PaperModule instance=new PaperModule();
    public boolean isPaper = WrappedObject.getRawClass(WrappedPaperConfig.class) != null;
	
	public boolean isPaper()
	{
		return isPaper;
	}
}

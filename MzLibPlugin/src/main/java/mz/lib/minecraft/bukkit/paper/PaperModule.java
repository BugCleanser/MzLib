package mz.lib.minecraft.bukkit.paper;

import mz.lib.minecraft.bukkitlegacy.MzLib;
import mz.lib.minecraft.bukkitlegacy.module.AbsModule;
import mz.lib.wrapper.WrappedObject;

public class PaperModule extends AbsModule
{
	public static PaperModule instance=new PaperModule();
    public boolean isPaper = WrappedObject.getRawClass(WrappedPaperConfig.class) != null;
	public PaperModule()
	{
		super(MzLib.instance);
	}
	
	public boolean isPaper()
	{
		return isPaper;
	}
}

package mz.lib.minecraft.bukkit.paper;

import mz.lib.minecraft.bukkit.MzLib;
import mz.lib.minecraft.bukkit.module.AbsModule;
import mz.lib.wrapper.WrappedObject;

public class PaperModule extends AbsModule
{
	public static PaperModule instance=new PaperModule();
    public boolean isPaper = WrappedObject.getRawClass(WrappedPaperConfig.class) != null;
	public PaperModule()
	{
		super(MzLib.instance);
	}
	
	@Override
	public void onEnable()
	{
	}

}

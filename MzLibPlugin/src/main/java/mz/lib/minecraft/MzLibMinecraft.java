package mz.lib.minecraft;

import mz.lib.*;
import mz.lib.module.*;

public class MzLibMinecraft extends MzModule
{
	public static MzLibMinecraft instance=new MzLibMinecraft();
	
	@Override
	public void onLoad()
	{
		reg(MzLib.instance);
	}
}

package mz.lib;

import mz.lib.event.*;
import mz.lib.module.*;
import mz.lib.nothing.*;

public class MzLib extends MzModule
{
	public static MzLib instance=new MzLib();
	
	@Override
	public void onLoad()
	{
		RegistrarRegistrar.instance.register(this,RegistrarRegistrar.instance);
		
		Nothing.init();
		reg(NothingRegistrar.instance);
		
		reg(EventListenerRegistrar.instance);
	}
	
	@Override
	public void onUnload()
	{
		Nothing.uninstallAll();
	}
}

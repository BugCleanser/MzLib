package mz.lib;

import mz.lib.event.*;
import mz.lib.lang.*;
import mz.lib.module.*;
import mz.lib.nothing.*;

import java.io.*;
import java.net.*;

public class MzLib extends MzModule
{
	public static MzLib instance=new MzLib();
	
	public File dataFolder=new File("./");
	public File jarFile;
	{
		try
		{
			jarFile=new File(((URLClassLoader)MzLib.class.getClassLoader()).getURLs()[0].toURI());
		}
		catch(Throwable e)
		{
			throw TypeUtil.throwException(e);
		}
	}
	
	@Override
	public void onLoad()
	{
		reg(RegistrableRegistrar.instance);
		
		Nothing.init();
		reg(NothingRegistrar.instance);
		
		reg(EventListenerRegistrar.instance);
		
		reg(Languages.instance);
	}
	
	@Override
	public void onUnload()
	{
		Nothing.uninstallAll();
	}

    public String getName() {
		return "mzlib";
    }
}

package mz.mzlib.minecraft.vanilla;

import mz.mzlib.MzLib;
import mz.mzlib.minecraft.event.server.EventServer;
import mz.mzlib.module.MzModule;

public class MzLibInitializer extends MzModule
{
    public static MzLibInitializer instance = new MzLibInitializer();
    
    @Override
    public void onLoad()
    {
        this.register(MzLib.instance);
        this.register(EventServer.Module.instance);
    }
}

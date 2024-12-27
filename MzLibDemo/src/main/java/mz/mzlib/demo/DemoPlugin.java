package mz.mzlib.demo;

import mz.mzlib.MzLib;
import org.bukkit.plugin.java.JavaPlugin;

public class DemoPlugin extends JavaPlugin
{
    @Override
    public void onEnable()
    {
        Demo.instance.jar=this.getFile();
        MzLib.instance.register(Demo.instance);
    }
    
    @Override
    public void onDisable()
    {
        MzLib.instance.unregister(Demo.instance);
    }
}
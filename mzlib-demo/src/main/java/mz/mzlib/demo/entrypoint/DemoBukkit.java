package mz.mzlib.demo.entrypoint;

import mz.mzlib.demo.Demo;
import org.bukkit.plugin.java.JavaPlugin;

public class DemoBukkit extends JavaPlugin
{
    @Override
    public void onEnable()
    {
        Demo.instance.jar = this.getFile();
        Demo.instance.dataFolder = this.getDataFolder();
        Demo.instance.load();
    }

    @Override
    public void onDisable()
    {
        Demo.instance.unload();
    }
}
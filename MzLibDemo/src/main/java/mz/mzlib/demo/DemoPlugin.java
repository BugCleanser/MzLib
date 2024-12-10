package mz.mzlib.demo;

import mz.mzlib.MzLib;
import mz.mzlib.demo.module.Demo;
import org.bukkit.plugin.java.JavaPlugin;

public class DemoPlugin extends JavaPlugin {
    @Override
    public void onLoad() {
        getServer().getLogger().info("MzLibDemo is Loaded!");
    }

    @Override
    public void onEnable() {
        MzLib.instance.register(Demo.instance);
    }

    @Override
    public void onDisable() {
        MzLib.instance.unregister(Demo.instance);
    }
}
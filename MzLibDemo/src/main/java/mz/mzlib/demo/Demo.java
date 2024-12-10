package mz.mzlib.demo;

import org.bukkit.plugin.java.JavaPlugin;

public class Demo extends JavaPlugin {
    @Override
    public void onLoad() {
        getServer().getLogger().info("MzLibDemo is Loaded!");
    }
}
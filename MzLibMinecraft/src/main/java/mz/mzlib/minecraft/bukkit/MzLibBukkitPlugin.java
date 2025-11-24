package mz.mzlib.minecraft.bukkit;

import mz.mzlib.minecraft.hook.Metrics;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class MzLibBukkitPlugin extends JavaPlugin {
    public static MzLibBukkitPlugin instance;

    {
        instance = this;
    }

    @Override
    public void onEnable() {
        MzLibBukkit.instance.load();
        new Metrics(this, 23141);
    }

    @Override
    public void onDisable() {
        MzLibBukkit.instance.unload();
    }

    @Override
    public File getFile() {
        return super.getFile();
    }
}

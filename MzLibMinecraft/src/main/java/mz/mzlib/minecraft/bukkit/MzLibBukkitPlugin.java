package mz.mzlib.minecraft.bukkit;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class MzLibBukkitPlugin extends JavaPlugin
{
    public static MzLibBukkitPlugin instance;
    {
        instance = this;
    }

    @Override
    public void onEnable()
    {
        MzLibBukkit.instance.load();
    }

    @Override
    public void onDisable()
    {
        MzLibBukkit.instance.unload();
    }
    
    @Override
    public File getFile()
    {
        return super.getFile();
    }
}

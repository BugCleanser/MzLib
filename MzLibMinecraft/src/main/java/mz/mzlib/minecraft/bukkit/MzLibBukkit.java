package mz.mzlib.minecraft.bukkit;

import mz.mzlib.minecraft.MzLibMinecraft;
import mz.mzlib.module.MzModule;
import org.bukkit.Bukkit;

public class MzLibBukkit extends MzModule
{
    public static MzLibBukkit instance = new MzLibBukkit();

    @Override
    public void onLoad()
    {
        if (MinecraftPlatformBukkit.instance.getVersion() < 1400 && MinecraftPlatformBukkit.instance.getVersion() != 1202 && MinecraftPlatformBukkit.instance.getVersion() != 1302)
            throw new IllegalStateException("MzLib is unsupported on your MC version: " + MinecraftPlatformBukkit.instance.getVersionString());

        this.register(MinecraftPlatformBukkit.instance);

        this.register(CraftServer.create(Bukkit.getServer()).getServer());
        
        this.register(BukkitWindowModule.instance);
        
        this.register(PermissionHelpBukkit.instance);
        
        this.register(MzLibMinecraft.instance);
    }
}

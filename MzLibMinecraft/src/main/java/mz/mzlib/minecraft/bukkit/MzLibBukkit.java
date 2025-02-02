package mz.mzlib.minecraft.bukkit;

import mz.mzlib.minecraft.MzLibMinecraft;
import mz.mzlib.minecraft.bukkit.command.RegistrarCommandBukkit;
import mz.mzlib.minecraft.bukkit.network.packet.ModuleBukkitPacketListener;
import mz.mzlib.module.MzModule;
import org.bukkit.Bukkit;

public class MzLibBukkit extends MzModule
{
    public static MzLibBukkit instance = new MzLibBukkit();

    @Override
    public void onLoad()
    {
        this.register(MinecraftPlatformBukkit.instance);

        this.register(CraftServer.create(Bukkit.getServer()).getServer());
        
        this.register(RegistrarCommandBukkit.instance);
        this.register(PermissionHelpBukkit.instance);
        
        this.register(ModuleBukkitWindow.instance);
        this.register(ModuleBukkitPacketListener.instance);
        
        this.register(MzLibMinecraft.instance);
    }
}

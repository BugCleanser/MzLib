package mz.mzlib.minecraft.bukkit;

import mz.mzlib.minecraft.MzLibMinecraft;
import mz.mzlib.minecraft.bukkit.command.RegistrarCommandBukkit;
import mz.mzlib.minecraft.bukkit.network.packet.ModuleBukkitPacketListener;
import mz.mzlib.minecraft.network.ClientConnection;
import mz.mzlib.module.MzModule;
import mz.mzlib.util.ClassUtil;
import org.bukkit.Bukkit;

import java.io.FileOutputStream;
import java.util.concurrent.ForkJoinPool;

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
        
        ForkJoinPool.commonPool().execute(()->
        {
            try(FileOutputStream fos=new FileOutputStream("test.class"))
            {
                Thread.sleep(15000);
                fos.write(ClassUtil.getByteCode(ClientConnection.create(null).staticGetWrappedClass()));
            }
            catch(Exception e)
            {
                throw new RuntimeException(e);
            }
        });
    }
}

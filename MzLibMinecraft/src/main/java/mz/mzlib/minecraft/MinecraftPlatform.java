package mz.mzlib.minecraft;

import mz.mzlib.minecraft.entity.player.EntityPlayer;
import mz.mzlib.minecraft.mappings.IMappings;
import mz.mzlib.util.Instance;
import mz.mzlib.util.RuntimeUtil;
import org.bukkit.Bukkit;

import java.io.File;

public interface MinecraftPlatform extends Instance
{
    MinecraftPlatform instance = RuntimeUtil.nul();
    
    String getVersionString();
    
    int getVersion();
    
    default boolean inVersion(VersionRange name)
    {
        return this.getVersion()>=name.begin() && this.getVersion()<name.end();
    }
    
    default boolean inVersion(VersionName name)
    {
        return this.getVersion()>=name.begin() && this.getVersion()<name.end();
    }
    
    String getLanguage(EntityPlayer player);
    
    File getMzLibJar();
    
    File getMzLibDataFolder();
    
    IMappings getMappingsP2Y(); // platform -> yarn
    
    IMappings getMappingsY2P(); // yarn -> platform
    
    static int parseVersion(String version)
    {
        String[] versions = version.split("\\.", -1);
        return Integer.parseInt(versions[1])*100+(versions.length>2 ? Integer.parseInt(versions[2]) : 0);
    }
}

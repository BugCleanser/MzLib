package mz.mzlib.minecraft.bukkit;

import mz.mzlib.minecraft.MinecraftPlatform;
import mz.mzlib.minecraft.bukkit.entity.BukkitEntityUtil;
import mz.mzlib.minecraft.entity.player.EntityPlayer;
import mz.mzlib.minecraft.mappings.*;
import mz.mzlib.util.RuntimeUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MinecraftPlatformBukkit implements MinecraftPlatform
{
    public static MinecraftPlatformBukkit instance = new MinecraftPlatformBukkit();
    
    @Override
    public String getLanguage(EntityPlayer player)
    {
        if(MinecraftPlatform.instance.getVersion()<1200)
            return this.getLanguageV_1200(player);
        else
            return this.getLanguageV1200(player);
    }
    @SuppressWarnings("deprecation")
    public String getLanguageV_1200(EntityPlayer player)
    {
        return ((Player)BukkitEntityUtil.toBukkit(player)).spigot().getLocale();
    }
    public String getLanguageV1200(EntityPlayer player)
    {
        return ((Player)BukkitEntityUtil.toBukkit(player)).getLocale();
    }
    
    public String protocolVersion;
    
    {
        String packageName = Bukkit.getServer().getClass().getPackage().getName().substring("org.bukkit.craftbukkit".length());
        protocolVersion = packageName.isEmpty() ? null : packageName.substring(".".length());
    }
    
    public String versionString;
    public int version;
    
    {
        this.versionString = Bukkit.getBukkitVersion().split("-")[0];
        this.version = MinecraftPlatform.parseVersion(this.versionString);
    }
    
    public boolean isPaper = RuntimeUtil.runAndCatch(()->Class.forName("com.destroystokyo.paper.PaperConfig"))==null || RuntimeUtil.runAndCatch(()->Class.forName("io.papermc.paper.configuration.Configuration"))==null;
    public boolean isPaper()
    {
        return this.isPaper;
    }
    
    @Override
    public String getVersionString()
    {
        return versionString;
    }
    @Override
    public int getVersion()
    {
        return version;
    }
    
    @Override
    public File getMzLibJar()
    {
        return MzLibBukkitPlugin.instance.getFile();
    }
    @Override
    public File getMzLibDataFolder()
    {
        return MzLibBukkitPlugin.instance.getDataFolder();
    }
    
    public Mappings<?> mappings;
    
    @Override
    public Mappings<?> getMappings()
    {
        if(this.mappings!=null)
            return this.mappings;
        try
        {
            File folder = new File(getMzLibDataFolder(), "mappings");
            List<Mappings<?>> result = new ArrayList<>();
            if(this.isPaper() && this.getVersion()>=2005)
                result.add(new MinecraftMappingsFetcherMojang().fetch(getVersionString(), folder));
            else
                result.add(new MinecraftMappingsFetcherSpigot().fetch(getVersionString(), this.protocolVersion, folder));
            if(this.getVersion()<1400)
            {
                // = new MinecraftMappingsFetcherLegacyYarnIntermediary().fetch(getVersionString(), folder);
                // TODO
                result.add(new MinecraftMappingsFetcherLegacyYarn().fetch(getVersionString(), folder));
            }
            else
            {
                result.add(new MinecraftMappingsFetcherYarnIntermediary().fetch(getVersionString(), folder));
                result.add(new MinecraftMappingsFetcherYarn().fetch(getVersionString(), folder));
            }
            return this.mappings =new MappingsPipe(result);
        }
        catch(Throwable e)
        {
            throw RuntimeUtil.sneakilyThrow(e);
        }
    }
}

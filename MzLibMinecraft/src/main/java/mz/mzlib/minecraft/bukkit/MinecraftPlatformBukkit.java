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
import java.util.Objects;

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
    public class SpigotPackageMappingV_1700 implements IMappings
    {
        public String nms = "net.minecraft.server."+MinecraftPlatformBukkit.this.protocolVersion;
        public String mapClass0(String from)
        {
            String result;
            if(from.startsWith(nms+'.'))
                result = from.substring((nms+'.').length());
            else
                result = from;
            if(result.equals("MinecraftServer"))
                return "net.minecraft.server."+result;
            return result;
        }
        public String mapField0(String fromClass, String fromField)
        {
            return null;
        }
        public String mapMethod0(String fromClass, MappingMethod fromMethod)
        {
            return null;
        }
    }
    
    public class SpigotPackageMappingReversedV_1700 implements IMappings
    {
        public String nms = "net.minecraft.server."+MinecraftPlatformBukkit.this.protocolVersion;
        public String mapClass0(String from)
        {
            if(from.equals("net.minecraft.server.MinecraftServer"))
                from = "MinecraftServer";
            if(!from.contains("."))
                return nms+"."+from;
            else
                return from;
        }
        public String mapField0(String fromClass, String fromField)
        {
            return null;
        }
        public String mapMethod0(String fromClass, MappingMethod fromMethod)
        {
            return null;
        }
    }
    
    public IMappings mappingsP2Y, mappingsY2P;
    
    {
        File folder = new File(getMzLibDataFolder(), "mappings");
        Mappings yarn;
        Mappings yarnIntermediary;
        Mappings platform;
        Mappings mappingsSpigot = null;
        try
        {
            if(this.getVersion()<1400)
            {
                yarn = new MinecraftMappingsFetcherLegacyYarn().fetch(getVersionString(), folder);
                yarnIntermediary = null; // = new MinecraftMappingsFetcherLegacyYarnIntermediary().fetch(getVersionString(), folder);
            }
            else
            {
                yarn = new MinecraftMappingsFetcherYarn().fetch(getVersionString(), folder);
                yarnIntermediary = new MinecraftMappingsFetcherYarnIntermediary().fetch(getVersionString(), folder);
            }
            if(this.isPaper() && this.getVersion()>=2005)
                platform = new MinecraftMappingsFetcherMojang().fetch(getVersionString(), folder);
            else
            {
                platform = new MinecraftMappingsFetcherSpigot().fetch(getVersionString(), folder);
                mappingsSpigot = platform;
            }
        }
        catch(Throwable e)
        {
            throw RuntimeUtil.sneakilyThrow(e);
        }
        
        List<IMappings> result = new ArrayList<>();
        if(getVersion()<1700)
            result.add(new SpigotPackageMappingV_1700());
        Mappings mappingsV1605_1700 = null;
        if(this.getVersion()==1605 && mappingsSpigot!=null)
        {
            mappingsV1605_1700 = new Mappings();
            for(String c: mappingsSpigot.classes.keySet())
            {
                if(c.contains("."))
                    mappingsV1605_1700.classes.put(c.substring(c.lastIndexOf('.')+1), c);
            }
            result.add(mappingsV1605_1700);
        }
        result.add(platform);
        if(yarnIntermediary!=null)
            result.add(yarnIntermediary);
        result.add(yarn);
        this.mappingsP2Y = new MappingsPipe(result);
        
        result = new ArrayList<>();
        result.add(yarn.reverse());
        if(yarnIntermediary!=null)
            result.add(yarnIntermediary.reverse());
        result.add(Objects.requireNonNull(platform).reverse());
        if(this.getVersion()==1605 && mappingsV1605_1700!=null)
            result.add(mappingsV1605_1700.reverse());
        if(getVersion()<1700)
            result.add(new SpigotPackageMappingReversedV_1700());
        this.mappingsY2P = new MappingsPipe(result);
    }
    
    @Override
    public IMappings getMappingsP2Y()
    {
        return this.mappingsP2Y;
    }
    @Override
    public IMappings getMappingsY2P()
    {
        return this.mappingsY2P;
    }
}

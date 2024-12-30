package mz.mzlib.minecraft.bukkit;

import mz.mzlib.minecraft.MinecraftPlatform;
import mz.mzlib.minecraft.bukkit.entity.BukkitEntityUtil;
import mz.mzlib.minecraft.entity.player.EntityPlayer;
import mz.mzlib.minecraft.mappings.*;
import mz.mzlib.util.Ref;
import mz.mzlib.util.RuntimeUtil;
import mz.mzlib.util.StrongRef;
import org.bukkit.Bukkit;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class MinecraftPlatformBukkit implements MinecraftPlatform
{
    public static MinecraftPlatformBukkit instance = new MinecraftPlatformBukkit();
    
    @Override
    public String getLanguage(EntityPlayer player)
    {
        return ((org.bukkit.entity.Player)BukkitEntityUtil.toBukkit(player)).getLocale();
    }
    
    @Override
    public Logger getMzLibLogger()
    {
        return MzLibBukkitPlugin.instance.getLogger();
    }
    
    public String protocolVersion;
    
    {
        String packageName = Bukkit.getServer().getClass().getPackage().getName().substring("org.bukkit.craftbukkit".length());
        protocolVersion = packageName.isEmpty() ? null : packageName.substring(".".length());
    }
    
    public String versionString;
    public int version;
    
    {
        versionString = Bukkit.getBukkitVersion().split("-")[0];
        String[] versions = versionString.split("\\.", -1);
        version = Integer.parseInt(versions[1])*100+(versions.length>2 ? Integer.parseInt(versions[2]) : 0);
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
        Ref<Mappings> yarnLegacy = new StrongRef<>(null);
        Ref<Mappings> yarn = new StrongRef<>(null);
        Ref<Mappings> yarnIntermediary = new StrongRef<>(null);
        Ref<Mappings> platform = new StrongRef<>(null);
        try
        {
            {
                YarnMappings y = new YarnMappingFetcher(getVersionString(), folder).fetch();
                if(y.legacy!=null)
                    yarnLegacy.set(Mappings.parseYarnLegacy(y.legacy));
                else
                {
                    yarn.set(Mappings.parseYarn(y.zip));
                    yarnIntermediary.set(Mappings.parseYarnIntermediary(y.intermediary));
                }
            }
            if(this.isPaper() && this.getVersion()>=2005)
                platform.set(Mappings.parseMojang(new MojangMappingsFetcher(getVersionString(), folder).fetch()));
            else
                platform.set(Mappings.parseSpigot(this.getVersion(), new SpigotMinecraftMappingsFetcher(getVersionString(), folder).fetch()));
        }
        catch(Throwable e)
        {
            throw RuntimeUtil.sneakilyThrow(e);
        }
        
        List<IMappings> result = new ArrayList<>();
        if(getVersion()<1700)
            result.add(new SpigotPackageMappingV_1700());
        result.add(platform.get());
        if(yarnLegacy.get()!=null)
            result.add(yarnLegacy.get());
        else
        {
            if(getVersion()>=1403)
                result.add(yarnIntermediary.get());
            result.add(yarn.get());
        }
        this.mappingsP2Y = new MappingsPipe(result);
        
        result = new ArrayList<>();
        if(yarnLegacy.get()!=null)
            result.add(yarnLegacy.get().reverse());
        else
        {
            result.add(yarn.get().reverse());
            if(getVersion()>=1403)
                result.add(yarnIntermediary.get().reverse());
        }
        result.add(platform.get().reverse());
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

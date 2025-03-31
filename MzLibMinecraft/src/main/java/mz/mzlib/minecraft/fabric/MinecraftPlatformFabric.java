package mz.mzlib.minecraft.fabric;

import mz.mzlib.minecraft.MinecraftPlatform;
import mz.mzlib.minecraft.MzLibMinecraft;
import mz.mzlib.minecraft.entity.player.EntityPlayer;
import mz.mzlib.minecraft.mappings.Mappings;
import mz.mzlib.minecraft.mappings.MinecraftMappingsFetcherYarn;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

public class  MinecraftPlatformFabric implements MinecraftPlatform
{
    public String versionString;
    public Integer version;
    public File mzLibJar;
    public File mzLibDataFolder;
    public Mappings<?> mappings;
    
    @Override
    public String getVersionString()
    {
        if(this.versionString!=null)
            return this.versionString;
        return this.versionString = FabricLoader.getInstance().getModContainer("minecraft").orElseThrow(AssertionError::new).getMetadata().getVersion().getFriendlyString();
    }
    @Override
    public int getVersion()
    {
        if(this.version!=null)
            return this.version;
        return this.version = MinecraftPlatform.parseVersion(this.getVersionString());
    }
    @Override
    public String getLanguage(EntityPlayer player)
    {
        // TODO
        return "zh_cn";
    }
    @Override
    public File getMzLibJar()
    {
        if(this.mzLibJar!=null)
            return this.mzLibJar;
        List<Path> paths = FabricLoader.getInstance().getModContainer(MzLibMinecraft.instance.MOD_ID).orElseThrow(AssertionError::new).getOrigin().getPaths();
        assert paths.size()==1;
        return this.mzLibJar = paths.get(0).toFile();
    }
    @Override
    public File getMzLibDataFolder()
    {
        if(this.mzLibDataFolder!=null)
            return this.mzLibDataFolder;
        return this.mzLibDataFolder = new File(this.getMzLibJar().getParentFile(), "MzLib");
    }
    
    @Override
    public Mappings<?> getMappings()
    {
        if(this.mappings!=null)
            return this.mappings;
        return this.mappings = new MinecraftMappingsFetcherYarn().fetch(getVersionString(), new File(getMzLibDataFolder(), "mappings"));
    }
}

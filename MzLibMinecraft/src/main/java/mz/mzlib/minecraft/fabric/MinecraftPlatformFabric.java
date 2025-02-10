package mz.mzlib.minecraft.fabric;

import mz.mzlib.minecraft.MinecraftPlatform;
import mz.mzlib.minecraft.entity.player.EntityPlayer;
import mz.mzlib.minecraft.mappings.Mappings;
import mz.mzlib.minecraft.mappings.MinecraftMappingsFetcherYarn;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

public class  MinecraftPlatformFabric implements MinecraftPlatform
{
    public static MinecraftPlatformFabric instance = new MinecraftPlatformFabric();
    
    public String versionString;
    public int version;
    public File mzLibJar;
    public File mzLibDataFolder;
    public Mappings<?> mappings;
    
    @Override
    public String getVersionString()
    {
        return this.versionString;
    }
    @Override
    public int getVersion()
    {
        return this.version;
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
        return this.mzLibJar;
    }
    @Override
    public File getMzLibDataFolder()
    {
        return this.mzLibDataFolder;
    }
    
    @Override
    public Mappings<?> getMappings()
    {
        if(this.mappings!=null)
            return this.mappings;
        this.versionString = FabricLoader.getInstance().getModContainer("minecraft").orElseThrow(AssertionError::new).getMetadata().getVersion().getFriendlyString();
        this.version = MinecraftPlatform.parseVersion(this.versionString);
        List<Path> paths = FabricLoader.getInstance().getModContainer(MzLibFabricInitializer.instance.MOD_ID).orElseThrow(AssertionError::new).getOrigin().getPaths();
        assert paths.size()==1;
        this.mzLibJar = paths.get(0).toFile();
        this.mzLibDataFolder = new File(this.mzLibJar.getParentFile(), "MzLib");
        File cacheMappings = new File(getMzLibDataFolder(), "mappings");
        return this.mappings = new MinecraftMappingsFetcherYarn().fetch(getVersionString(), cacheMappings);
    }
}

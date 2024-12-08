package mz.mzlib.minecraft.mappings;

import java.io.File;

public abstract class MinecraftMappingsFetcher<T>
{
    public String version;
    public File cacheFolder;
    
    public MinecraftMappingsFetcher(String version, File cacheFolder)
    {
        this.version = version;
        this.cacheFolder = cacheFolder;
    }
    
    public abstract T fetch();
}

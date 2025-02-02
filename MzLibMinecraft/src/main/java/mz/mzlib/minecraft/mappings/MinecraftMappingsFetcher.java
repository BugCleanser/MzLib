package mz.mzlib.minecraft.mappings;

import java.io.File;
import java.io.IOException;

public interface MinecraftMappingsFetcher
{
    Mappings fetch(String version, File cacheFolder) throws IOException;
}

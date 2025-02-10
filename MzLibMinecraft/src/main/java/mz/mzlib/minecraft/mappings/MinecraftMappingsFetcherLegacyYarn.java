package mz.mzlib.minecraft.mappings;

import mz.mzlib.minecraft.MinecraftPlatform;
import mz.mzlib.util.IOUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class MinecraftMappingsFetcherLegacyYarn implements MinecraftMappingsFetcher
{
    @Override
    public MappingsByMap fetch(String version, File cacheFolder) throws IOException
    {
        try(InputStream is = IOUtil.openFileInZip(MinecraftPlatform.instance.getMzLibJar(), "mappings/yarn/"+version+".tiny"))
        {
            return MappingsByMap.parseYarnLegacy(new String(IOUtil.readAll(is), StandardCharsets.UTF_8));
        }
    }
}

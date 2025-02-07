package mz.mzlib.minecraft.mappings;

import mz.mzlib.minecraft.MinecraftPlatform;
import mz.mzlib.util.IOUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class MinecraftMappingsFetcherLegacyYarn implements MinecraftMappingsFetcher
{
    @Override
    public Mappings fetch(String version, File cacheFolder) throws IOException
    {
        try(InputStream is = new URL("jar", "", -1, MinecraftPlatform.instance.getMzLibJar().toURI().toURL()+"!/mappings/yarn/"+version+".tiny").openConnection().getInputStream())
        {
            return Mappings.parseYarnLegacy(new String(IOUtil.readAll(is), StandardCharsets.UTF_8));
        }
    }
}

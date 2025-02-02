package mz.mzlib.minecraft.mappings;

import mz.mzlib.util.IOUtil;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class MinecraftMappingsFetcherLegacyYarn implements MinecraftMappingsFetcher
{
    @Override
    public Mappings fetch(String version, File cacheFolder) throws IOException
    {
        return Mappings.parseYarnLegacy(new String(IOUtil.readAll(Objects.requireNonNull(this.getClass().getResourceAsStream("/mappings/yarn/"+version+".tiny"))), StandardCharsets.UTF_8));
    }
}

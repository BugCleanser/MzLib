package mz.mzlib.minecraft.mappings;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.Optional;
import java.util.zip.ZipInputStream;

public class MinecraftMappingsFetcherYarn implements MinecraftMappingsFetcher
{
    @Override
    public MappingsByMap fetch(String version, File cacheFolder)
    {
        return MappingsByMap.parseZipMapping(new ZipInputStream(new ByteArrayInputStream(MappingsUtil.cache0(Optional.ofNullable(cacheFolder).map(it->new File(new File(it, "Yarn"), version+".zip")).orElse(null), ()->MappingsUtil.request0("https://codeload.github.com/FabricMC/yarn/zip/refs/heads/"+version)))), "yarn-"+version+"/mappings/");
    }
}

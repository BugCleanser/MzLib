package mz.mzlib.minecraft.mappings;

import java.io.File;
import java.util.Optional;

public class MinecraftMappingsFetcherLegacyYarnIntermediary implements MinecraftMappingsFetcher
{
    @Override
    public Mappings fetch(String version, File cacheFolder)
    {
        return Mappings.parseTiny(MappingsUtil.cache(Optional.ofNullable(cacheFolder).map(it->new File(new File(it, "YarnIntermediary"), version+".tiny")).orElse(null), ()->MappingsUtil.request("https://raw.githubusercontent.com/Legacy-Fabric/Legacy-Intermediaries/refs/heads/v2/legacy/mappings/"+version+".tiny")));
    }
}

package mz.mzlib.minecraft.mappings;

import java.io.File;
import java.util.Optional;

public class MinecraftMappingsFetcherYarnIntermediary implements MinecraftMappingsFetcher
{
    @Override
    public MappingsByMap fetch(String version, File cacheFolder)
    {
        return MappingsByMap.parseTiny(MappingsUtil.cache(Optional.ofNullable(cacheFolder).map(it->new File(new File(it, "YarnIntermediary"), version+".tiny")).orElse(null), ()->MappingsUtil.request("https://raw.githubusercontent.com/FabricMC/intermediary/master/mappings/"+version+".tiny")));
    }
}

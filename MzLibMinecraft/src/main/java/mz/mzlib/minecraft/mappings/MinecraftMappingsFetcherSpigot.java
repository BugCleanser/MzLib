package mz.mzlib.minecraft.mappings;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import mz.mzlib.util.CachedValue;

import java.io.File;
import java.util.Objects;
import java.util.Optional;

/**
 * get: https://hub.spigotmc.org/versions/%version%.json
 * commit: parseJson -> refs -> BuildData
 * <p>
 * get https://hub.spigotmc.org/stash/projects/SPIGOT/repos/builddata/raw/info.json?at=%commit%
 * parseJson ->
 * accessTransformName: fileName
 * classMappingsName: fileName
 * memberMappingsName: fileName
 * <p>
 * accessTransform: get: https://hub.spigotmc.org/stash/projects/SPIGOT/repos/builddata/raw/mappings/%accessTransformName%?at=%commit%
 * classMappings: get: https://hub.spigotmc.org/stash/projects/SPIGOT/repos/builddata/raw/mappings/%classMappingsName%?at=%commit%
 * memberMappings: get: https://hub.spigotmc.org/stash/projects/SPIGOT/repos/builddata/raw/mappings/%memberMappingsName%?at=%commit%
 */
public class MinecraftMappingsFetcherSpigot implements MinecraftMappingsFetcher
{
    public String baseUrl = "https://hub.spigotmc.org/stash/projects/SPIGOT/repos/builddata/raw/";
    
    @Override
    public Mappings fetch(String version, File cacheFolder)
    {
        CachedValue<String> refsGetter = new CachedValue<>(()->Objects.requireNonNull(new Gson().fromJson(MappingsUtil.request(MappingsUtil.url("https://hub.spigotmc.org/versions/"+version+".json")), JsonObject.class)).getAsJsonObject("refs").get("BuildData").getAsString());
        CachedValue<JsonObject> infoGetter = new CachedValue<>(()->new Gson().fromJson(MappingsUtil.request(MappingsUtil.url(baseUrl+"info.json"+"?at="+refsGetter.get())), JsonObject.class));
        
        Mappings result = Mappings.parseCsrg(MappingsUtil.cache(Optional.ofNullable(cacheFolder).map(it->new File(new File(it, "Spigot"), version+".csrg")).orElse(null), ()->MappingsUtil.request(MappingsUtil.url(baseUrl+"mappings/"+infoGetter.get().get("classMappings").getAsString()+"?at="+refsGetter.get()))), MappingsUtil.cache(Optional.ofNullable(cacheFolder).map(it->new File(new File(it, "Spigot"), version+"-members.csrg")).orElse(null), ()->Optional.ofNullable(infoGetter.get().get("memberMappings")).map(it->MappingsUtil.request(baseUrl+"mappings/"+infoGetter.get().get("memberMappings").getAsString()+"?at="+refsGetter.get())).orElse("")));
        if(!result.classes.containsKey("MinecraftServer"))
            result.classes.put("MinecraftServer", "net.minecraft.server.MinecraftServer");
        return result;
    }
}

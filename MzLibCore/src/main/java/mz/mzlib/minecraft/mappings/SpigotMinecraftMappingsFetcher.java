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
public class SpigotMinecraftMappingsFetcher extends MinecraftMappingsFetcher<SpigotMappings>
{
    public String baseUrl = "https://hub.spigotmc.org/stash/projects/SPIGOT/repos/builddata/raw/";
    
    public SpigotMinecraftMappingsFetcher(String version, File cacheFolder)
    {
        super(version, cacheFolder);
    }
    
    @Override
    public SpigotMappings fetch()
    {
        CachedValue<String> refsGetter = new CachedValue<>(()->Objects.requireNonNull(new Gson().fromJson(Util.request(Util.url("https://hub.spigotmc.org/versions/"+version+".json")), JsonObject.class)).getAsJsonObject("refs").get("BuildData").getAsString());
        CachedValue<JsonObject> infoGetter = new CachedValue<>(()->new Gson().fromJson(Util.request(Util.url(baseUrl+"info.json"+"?at="+refsGetter.get())), JsonObject.class));
        
        return new SpigotMappings(
                Util.cache(Optional.ofNullable(this.cacheFolder).map(it->new File(new File(it, "Spigot"), this.version+".at")).orElse(null), ()->Util.request(Util.url(baseUrl+"mappings/"+infoGetter.get().get("accessTransforms").getAsString()+"?at="+refsGetter.get()))),
                Util.cache(Optional.ofNullable(this.cacheFolder).map(it->new File(new File(it, "Spigot"), this.version+".csrg")).orElse(null), ()->Util.request(Util.url(baseUrl+"mappings/"+infoGetter.get().get("classMappings").getAsString()+"?at="+refsGetter.get()))),
                Util.cache(Optional.ofNullable(this.cacheFolder).map(it->new File(new File(it, "Spigot"), this.version+"-members.csrg")).orElse(null), ()->Optional.ofNullable(infoGetter.get().get("memberMappings")).map(it->Util.request(baseUrl+"mappings/"+infoGetter.get().get("memberMappings").getAsString()+"?at="+refsGetter.get())).orElse("")));
    }
}

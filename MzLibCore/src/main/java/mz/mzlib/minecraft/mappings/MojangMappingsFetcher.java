package mz.mzlib.minecraft.mappings;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import mz.mzlib.util.async.AsyncFunction;

import java.io.File;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.StreamSupport;

/**
 * get: https://piston-meta.mojang.com/mc/game/version_manifest_v2.json
 * manifests[]: parseJson -> versions
 * manifest: manifests -> id == %version%
 * url: manifest -> url
 * <p>
 * //fun hash(): Hash = Hash(sha1, HashingAlgorithm.SHA1)
 * <p>
 * get: url
 * mappings: parseJson -> downloads -> server_mappings
 * sha1: mappings -> sha1
 * url: mappings -> mappingsUrl
 * <p>
 * get: mappingsUrl
 */
public class MojangMappingsFetcher extends MinecraftMappingsFetcher<String>
{
    public MojangMappingsFetcher(String version, File cacheFolder)
    {
        super(version, cacheFolder);
    }

    /**
     * 此答辩由mz拉
     * @author MZ
     */
    @Override
    public String fetch()
    {
        return Util.cache(Optional.ofNullable(this.cacheFolder).map(it->new File(new File(it,"Mojang"),this.version+".txt")).orElse(null), ()->Util.request(new Gson().fromJson(Util.request(
                StreamSupport.stream(new Gson().fromJson(Util.request("https://piston-meta.mojang.com/mc/game/version_manifest_v2.json"), JsonObject.class).getAsJsonArray("versions").spliterator(), false)
                        .map(it -> ((JsonObject) it))
                        .filter(it->it.get("id").getAsString().equals(version)).findFirst().orElseThrow(() -> new RuntimeException("mojang not found version" + version))
                        .get("url").getAsString()
        ), JsonObject.class).getAsJsonObject("downloads").getAsJsonObject("server_mappings").get("url").getAsString()));
    }
}

package mz.mzlib.minecraft.mappings;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

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
public class MojangMappingsFetcher extends MappingFetcher<String>
{
    public MojangMappingsFetcher(String version, File cacheDir)
    {
        super(version, cacheDir);
    }

    /**
     * 此答辩由mz拉
     * @author MZ
     */
    public CompletableFuture<String> fetch()
    {
        return Util.cache(Optional.ofNullable(this.cacheFolder).map(it->new File(new File(it,"Mojang"),this.version+".txt")).orElse(null), this::fetch0);
    }

    /**
     * @author KyleSteven
     */
    private CompletableFuture<String> fetch0() {
        return Util.request("https://piston-meta.mojang.com/mc/game/version_manifest_v2.json")
                .thenApply(str -> new Gson().fromJson(str, JsonObject.class))
                .thenApply(json -> json.getAsJsonArray("versions"))
                .thenApply(json ->
                        StreamSupport.stream(json.spliterator(), false).map(it -> ((JsonObject) it))
                                .filter(it->it.get("id").getAsString().equals(version))
                                .findFirst()
                                .orElseThrow(() -> new RuntimeException("mojmap not found version" + version)))
                .thenApply(json -> json.get("url").getAsString())
                .thenCompose(Util::request)

                .thenApply(str -> new Gson().fromJson(str, JsonObject.class))
                .thenApply(json -> json.getAsJsonObject("downloads"))
                .thenApply(json -> json.getAsJsonObject("server_mappings"))

                .thenApply(it -> it.get("url").getAsString())
                .thenCompose(Util::request);
    }
}

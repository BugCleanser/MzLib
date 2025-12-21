package mz.mzlib.minecraft.mappings;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.File;
import java.util.Optional;
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
public class MinecraftMappingsFetcherMojang implements MinecraftMappingsFetcher
{
    /**
     * 此答辩由 mz 拉
     *
     * @author MZ
     */
    @Override
    public MappingsByMap fetch(String version, File cacheFolder)
    {
        return MappingsByMap.parseMojang(MappingsUtil.cache(
            Optional.ofNullable(cacheFolder).map(it -> new File(new File(it, "Mojang"), version + ".txt")).orElse(null),
            () -> MappingsUtil.request(new Gson().fromJson(
                MappingsUtil.request(
                    StreamSupport.stream(
                            new Gson().fromJson(
                                MappingsUtil.request("https://piston-meta.mojang.com/mc/game/version_manifest_v2.json"),
                                JsonObject.class
                            ).getAsJsonArray("versions").spliterator(), false
                        )
                        .map(it -> ((JsonObject) it))
                        .filter(it -> it.get("id").getAsString().equals(version)).findFirst()
                        .orElseThrow(() -> new RuntimeException("mojang not found version" + version))
                        .get("url").getAsString()
                ), JsonObject.class
            ).getAsJsonObject("downloads").getAsJsonObject("server_mappings").get("url").getAsString())
        ));
    }
}

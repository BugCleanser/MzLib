package mz.mzlib.minecraft.mappings;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.File;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

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
public class SpigotMappingsFetcher extends MappingFetcher<SpigotMappings>
{
    private final String baseUrl = "https://hub.spigotmc.org/stash/projects/SPIGOT/repos/builddata/raw/";

    public SpigotMappingsFetcher(String version, File cacheDir)
    {
        super(version, cacheDir);
    }

    @Override
    public CompletableFuture<SpigotMappings> fetch()
    {
        CachedValue<CompletableFuture<String>> refsGetter = new CachedValue<>(() ->
                Util.request(Util.url("https://hub.spigotmc.org/versions/" + version + ".json")).thenApply(body -> Objects.requireNonNull(new Gson().fromJson(body, JsonObject.class)).getAsJsonObject("refs").get("BuildData").getAsString()));
        CachedValue<CompletableFuture<JsonObject>> infoGetter = new CachedValue<>(() ->
                refsGetter.get().thenCompose(refs -> Util.request(Util.url(baseUrl + "info.json" + "?at=" + refs)).thenApply(str -> new Gson().fromJson(str, JsonObject.class))));


        CompletableFuture<String> responseTransform = Util.cache(Optional.ofNullable(this.cacheFolder)
                .map(it -> new File(new File(it, "Spigot"), this.version + ".at"))
                .orElse(null), () -> refsGetter.get().thenCompose(refs -> infoGetter.get().thenCompose(info -> Util.request(Util.url(baseUrl + "mappings/" + info.get("accessTransforms").getAsString() + "?at=" + refs)))));
        CompletableFuture<String> responseClass = Util.cache(Optional.ofNullable(this.cacheFolder).map(it -> new File(new File(it, "Spigot"), this.version + ".csrg")).orElse(null), () -> refsGetter.get().thenCompose(refs -> infoGetter.get().thenCompose(info -> Util.request(Util.url(baseUrl + "mappings/" + info.get("classMappings").getAsString() + "?at=" + refs)))));
        CompletableFuture<String> responseMember = Util.cache(Optional.ofNullable(this.cacheFolder).map(it -> new File(new File(it, "Spigot"), this.version + "-members.csrg")).orElse(null), () -> infoGetter.get().thenCompose(info -> Optional.ofNullable(info.get("memberMappings")).map(it ->
                refsGetter.get().thenCompose(refs -> Util.request(baseUrl + "mappings/" + info.get("memberMappings").getAsString() + "?at=" + refs))).orElse(CompletableFuture.completedFuture(""))));

        return responseTransform.thenApply(transform -> Util.runCatching(() ->
                new SpigotMappings(
                        transform,
                        responseClass.get(),
                        responseMember.get())));
    }
}

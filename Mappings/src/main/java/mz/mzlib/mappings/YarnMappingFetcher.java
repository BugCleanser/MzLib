package mz.mzlib.mappings;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.zip.ZipInputStream;

public class YarnMappingFetcher extends MappingFetcher<YarnMappings>
{
    public YarnMappingFetcher(String version, File cacheDir)
    {
        super(version,cacheDir);
    }

    public CompletableFuture<YarnMappings> fetch()
    {
        try(InputStream res=YarnMappingFetcher.class.getClassLoader().getResourceAsStream("mappings/yarn/" + this.version + ".tiny"))
        {
            if(res!=null)
                return CompletableFuture.completedFuture(new YarnMappings(new String(Util.readInputStream(res), StandardCharsets.UTF_8)));
        }
        catch (Throwable e)
        {
            throw Util.sneakilyThrow(e);
        }
        CompletableFuture<YarnMappings> future = new CompletableFuture<>();
        CompletableFuture<String> f = Util.cache(Optional.ofNullable(this.cacheFolder).map(it->new File(new File(it,"YarnIntermediary"),this.version+".tiny")).orElse(null),()->
                        Util.request("https://raw.githubusercontent.com/FabricMC/intermediary/master/mappings/" + this.version + ".tiny").exceptionally(e->
                        {
                            if(e instanceof FileNotFoundException || e instanceof CompletionException&&e.getCause() instanceof FileNotFoundException)
                                return "";
                            else
                                throw Util.sneakilyThrow(e);
                        }));
        Util.cache0(Optional.ofNullable(this.cacheFolder).map(it->new File(new File(it,"Yarn"),this.version+".zip")).orElse(null),()->
                Util.request0(Util.url("https://codeload.github.com/FabricMC/yarn/zip/refs/heads/" + this.version))).whenComplete((r, e) ->
        {
            if (e != null)
                future.completeExceptionally(e);
            f.whenComplete((r1, e1) ->
            {
                if (e1 != null)
                {
                    future.completeExceptionally(e1);
                    return;
                }
                future.complete(new YarnMappings(new ZipInputStream(new ByteArrayInputStream(r)), r1));
            });
        });
        return future;
    }
}

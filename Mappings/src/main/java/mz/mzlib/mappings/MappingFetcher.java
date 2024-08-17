package mz.mzlib.mappings;

import java.io.File;
import java.util.concurrent.CompletableFuture;

public abstract class MappingFetcher<T>
{
    public String version;
    public File cacheFolder;

    public MappingFetcher(String version, File cacheFolder)
    {
        this.version = version;
        this.cacheFolder = cacheFolder;
    }

    public abstract CompletableFuture<T> fetch();
}

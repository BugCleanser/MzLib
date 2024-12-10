package mz.mzlib.minecraft.mappings;

import mz.mzlib.util.RuntimeUtil;
import mz.mzlib.util.async.AsyncFunction;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.Executor;
import java.util.zip.ZipInputStream;

public class YarnMappingFetcher extends MinecraftMappingsFetcher<YarnMappings>
{
    public YarnMappingFetcher(String version, File cacheFolder)
    {
        super(version, cacheFolder);
    }
    
    @Override
    public YarnMappings fetch()
    {
        try(InputStream res = YarnMappingFetcher.class.getClassLoader().getResourceAsStream("mappings/yarn/"+version+".tiny"))
        {
            if(res!=null)
                return new YarnMappings(new String(Util.readInputStream(res), StandardCharsets.UTF_8));
        }
        catch(Throwable e)
        {
            throw RuntimeUtil.sneakilyThrow(e);
        }
        return new YarnMappings(new ZipInputStream(new ByteArrayInputStream(Util.cache0(Optional.ofNullable(cacheFolder).map(it->new File(new File(it, "Yarn"), version+".zip")).orElse(null), ()->Util.request0("https://codeload.github.com/FabricMC/yarn/zip/refs/heads/"+version)))), Util.cache(Optional.ofNullable(cacheFolder).map(it->new File(new File(it, "YarnIntermediary"), version+".tiny")).orElse(null), ()->Util.request("https://raw.githubusercontent.com/FabricMC/intermediary/master/mappings/"+version+".tiny")));
    }
}

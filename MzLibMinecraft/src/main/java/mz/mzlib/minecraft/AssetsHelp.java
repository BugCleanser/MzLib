package mz.mzlib.minecraft;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import mz.mzlib.util.IOUtil;
import mz.mzlib.util.LazyConstant;
import mz.mzlib.util.RuntimeUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.NoSuchFileException;
import java.util.Iterator;
import java.util.Map;

public class AssetsHelp implements Iterable<String>
{
    public static AssetsHelp instance = new AssetsHelp();
    
    public int retry = 10;
    public String defaultHost = "launchermeta.mojang.com";
    public String getHost()
    {
        return MzLibMinecraft.instance.config.getString("assets_host", defaultHost);
    }
    public URL replaceHost(URL url) throws MalformedURLException
    {
        if(MzLibMinecraft.instance.config.getString("assets_host")==null)
            return url;
        return new URL("https", MzLibMinecraft.instance.config.getString("assets_host"), -1, url.getFile());
    }
    public LazyConstant<JsonObject> versionManifest = new LazyConstant<>(()->
    {
        try(InputStream is = IOUtil.openConnectionCheckRedirects(new URL("https", getHost(), -1, "/mc/game/version_manifest.json"), retry))
        {
            return new Gson().fromJson(new String(IOUtil.readAll(is), StandardCharsets.UTF_8), JsonObject.class);
        }
        catch(Throwable e)
        {
            throw RuntimeUtil.sneakilyThrow(e);
        }
    });
    public LazyConstant<JsonObject> versionInfo = new LazyConstant<>(()->
    {
        try
        {
            return new Gson().fromJson(new String(IOUtil.cache(new File(MinecraftPlatform.instance.getMzLibDataFolder(), "assets/versionInfo-"+MinecraftPlatform.instance.getVersionString()+".json"), ()->
            {
                for(JsonElement version: versionManifest.get().get("versions").getAsJsonArray())
                {
                    if(version.getAsJsonObject().get("id").getAsString().equals(MinecraftPlatform.instance.getVersionString()))
                    {
                        URL url = replaceHost(new URL(version.getAsJsonObject().get("url").getAsString()));
                            try(InputStream is = IOUtil.openConnectionCheckRedirects(url, retry))
                            {
                                return IOUtil.readAll(is);
                            }
                    }
                }
                throw new AssertionError();
            }), StandardCharsets.UTF_8), JsonObject.class);
        }
        catch(Throwable e)
        {
            throw RuntimeUtil.sneakilyThrow(e);
        }
    });
    public LazyConstant<JsonObject> assetIndex = new LazyConstant<>(()->
    {
        try
        {
            return new Gson().fromJson(new String(IOUtil.cache(new File(MinecraftPlatform.instance.getMzLibDataFolder(), "assets/assetIndex-"+MinecraftPlatform.instance.getVersionString()+".json"), ()->
            {
                try(InputStream is = IOUtil.openConnectionCheckRedirects(replaceHost(new URL(versionInfo.get().get("assetIndex").getAsJsonObject().get("url").getAsString())), retry))
                {
                    return IOUtil.readAll(is);
                }
            }), StandardCharsets.UTF_8), JsonObject.class).get("objects").getAsJsonObject();
        }
        catch(Throwable e)
        {
            throw RuntimeUtil.sneakilyThrow(e);
        }
    });
    
    public class AssetsIterator implements Iterator<String>
    {
        public Iterator<Map.Entry<String, JsonElement>> i = AssetsHelp.this.assetIndex.get().entrySet().iterator();
        @Override
        public boolean hasNext()
        {
            return i.hasNext();
        }
        @Override
        public String next()
        {
            return i.next().getKey();
        }
    }
    @SuppressWarnings("NullableProblems")
    @Override
    public AssetsIterator iterator()
    {
        return new AssetsIterator();
    }
    public byte[] getAsset(String file) throws IOException
    {
        if(file.contains(".."))
            throw new NoSuchFileException("Minecraft assets: "+file);
        return IOUtil.cache(new File(MinecraftPlatform.instance.getMzLibDataFolder(), "assets/"+MinecraftPlatform.instance.getVersionString()+"/"+file), ()->
        {
            try(InputStream is = AssetsHelp.class.getClassLoader().getResourceAsStream("assets/"+file))
            {
                if(is!=null)
                    return IOUtil.readAll(is);
            }
            JsonElement jsonElement = this.assetIndex.get().get(file);
            if(jsonElement==null)
                throw new NoSuchFileException("Minecraft assets: "+file);
            String hash = jsonElement.getAsJsonObject().get("hash").getAsString();
            try(InputStream is = IOUtil.openConnectionCheckRedirects(new URL("https", this.getHost(), -1, "/assets/"+hash.substring(0, 2)+"/"+hash), retry))
            {
                return IOUtil.readAll(is);
            }
        });
    }
}

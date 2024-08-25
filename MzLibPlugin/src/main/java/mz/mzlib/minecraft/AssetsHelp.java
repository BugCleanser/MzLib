package mz.mzlib.minecraft;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import mz.mzlib.util.IOUtil;
import mz.mzlib.util.InitializableConstant;
import mz.mzlib.util.RuntimeUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.NoSuchFileException;
import java.util.Iterator;
import java.util.Map;

public class AssetsHelp implements Iterable<String>
{
    public static AssetsHelp instance=new AssetsHelp();

    public String host="bmclapi2.bangbang93.com"; // launchermeta.mojang.com
    public InitializableConstant<JsonObject> versionManifest=new InitializableConstant<>(()->
    {
        try (InputStream is = IOUtil.openConnectionCheckRedirects(new URL("https",host!=null?host:"launchermeta.mojang.com",-1,"mc/game/version_manifest.json").openConnection()))
        {
            return new Gson().fromJson(new String(IOUtil.readAll(is), StandardCharsets.UTF_8),JsonObject.class);
        }
        catch (Throwable e)
        {
            throw RuntimeUtil.sneakilyThrow(e);
        }
    });
    public InitializableConstant<JsonObject> versionInfo=new InitializableConstant<>(()->
    {
        try
        {
            for (JsonElement version : versionManifest.get().get("versions").getAsJsonArray())
            {
                if (version.getAsJsonObject().get("id").getAsString().equals(MinecraftPlatform.instance.getVersionString()))
                {
                    URL url=new URL("https",host,-1,new URL(version.getAsJsonObject().get("url").getAsString()).getFile());
                    return new Gson().fromJson(new String(IOUtil.cache(new File(MinecraftPlatform.instance.getMzLibDataFolder(),"assets/versionInfo-"+MinecraftPlatform.instance.getVersionString()+".json"),()->
                    {
                        try (InputStream is = IOUtil.openConnectionCheckRedirects(url.openConnection()))
                        {
                            return IOUtil.readAll(is);
                        }
                    }), StandardCharsets.UTF_8), JsonObject.class);
                }
            }
            throw new AssertionError();
        }
        catch (Throwable e)
        {
            throw RuntimeUtil.sneakilyThrow(e);
        }
    });
    public InitializableConstant<JsonObject> assetIndex=new InitializableConstant<>(()->
    {
        try
        {
            URL url=new URL("https",host,-1,new URL(versionInfo.get().get("assetIndex").getAsJsonObject().get("url").getAsString()).getFile());
            return new Gson().fromJson(new String(IOUtil.cache(new File(MinecraftPlatform.instance.getMzLibDataFolder(),"assets/assetIndex-"+MinecraftPlatform.instance.getVersionString()+".json"),()->
            {
                try (InputStream is = IOUtil.openConnectionCheckRedirects(url.openConnection()))
                {
                    return IOUtil.readAll(is);
                }
            }), StandardCharsets.UTF_8), JsonObject.class).get("objects").getAsJsonObject();
        }
        catch (Throwable e)
        {
            throw RuntimeUtil.sneakilyThrow(e);
        }
    });

    public class AssetsIterator implements Iterator<String>
    {
        public Iterator<Map.Entry<String, JsonElement>> i=AssetsHelp.this.assetIndex.get().entrySet().iterator();
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
    @Override
    public AssetsIterator iterator()
    {
        return new AssetsIterator();
    }
    public byte[] getAsset(String file) throws IOException
    {
        if(file.contains(".."))
            throw new NoSuchFileException("Minecraft assets: "+file);
        return IOUtil.cache(new File(MinecraftPlatform.instance.getMzLibDataFolder(), "assets/" + MinecraftPlatform.instance.getVersionString() + "/" + file), () ->
        {
            try (InputStream is = AssetsHelp.class.getClassLoader().getResourceAsStream("assets/" + file))
            {
                if (is != null)
                    return IOUtil.readAll(is);
            }
            JsonElement jsonElement = this.assetIndex.get().get(file);
            if(jsonElement==null)
                throw new NoSuchFileException("Minecraft assets: "+file);
            String hash = jsonElement.getAsJsonObject().get("hash").getAsString();
            try (InputStream is = IOUtil.openConnectionCheckRedirects(new URL("https", host, -1, "assets/" + hash.substring(0, 2) + "/" + hash).openConnection()))
            {
                return IOUtil.readAll(is);
            }
        });
    }
}

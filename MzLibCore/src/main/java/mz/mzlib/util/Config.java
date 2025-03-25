package mz.mzlib.util;

import com.google.gson.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class Config
{
    public JsonObject json;
    public Config(JsonObject json)
    {
        this.json = json;
    }
    
    public JsonElement get(String path, JsonElement def)
    {
        JsonElement result = this.json;
        for(String key: path.split("\\."))
        {
            result = result.getAsJsonObject().get(key);
            if(result==null)
                return def;
        }
        return result;
    }
    public JsonElement get(String path)
    {
        return this.get(path, null);
    }
    public String getString(String path, String def)
    {
        return this.get(path, new JsonPrimitive(def)).getAsString();
    }
    public String getString(String path)
    {
        JsonElement result = this.get(path, null);
        if(result==null)
            return null;
        return result.getAsString();
    }
    
    public static Config loadJson(InputStream def, File file) throws IOException
    {
        JsonObject json = new Gson().fromJson(new String(IOUtil.readAll(def), StandardCharsets.UTF_8), JsonObject.class);
        if(file.isFile())
        {
            JsonObject cnt;
            try(FileInputStream fis = new FileInputStream(file))
            {
                cnt = new Gson().fromJson(new String(IOUtil.readAll(fis), StandardCharsets.UTF_8), JsonObject.class);
            }
            merge(cnt, json);
            json = cnt;
        }
        else
        {
            boolean ignored = file.getParentFile().mkdirs();
        }
        try(FileOutputStream fos = new FileOutputStream(file))
        {
            fos.write(new GsonBuilder().setPrettyPrinting().create().toJson(json).getBytes(StandardCharsets.UTF_8));
        }
        return new Config(json);
    }
    @Deprecated
    public static Config load(InputStream def, File file) throws IOException
    {
        return loadJson(def, file);
    }
    
    public static void merge(JsonObject json, JsonObject def)
    {
        for(Map.Entry<String, JsonElement> i: def.entrySet())
        {
            if(!json.has(i.getKey()))
                json.add(i.getKey(), i.getValue());
            else if(i.getValue() instanceof JsonObject && json.get(i.getKey()) instanceof JsonObject)
                merge(json.getAsJsonObject(i.getKey()), i.getValue().getAsJsonObject());
        }
    }
}

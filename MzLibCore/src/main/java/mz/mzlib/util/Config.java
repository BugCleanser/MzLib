package mz.mzlib.util;

import com.google.gson.*;

import java.io.*;
import java.nio.charset.StandardCharsets;

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
    
    public static Config load(InputStream def, File file) throws IOException
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
        try(FileOutputStream fos = new FileOutputStream(file))
        {
            fos.write(new GsonBuilder().setPrettyPrinting().create().toJson(json).getBytes(StandardCharsets.UTF_8));
        }
        return new Config(json);
    }
    
    public static void merge(JsonObject json, JsonObject def)
    {
        for(String key: def.keySet())
        {
            JsonElement value = def.get(key);
            if(!json.has(key))
                json.add(key, value);
            else if(value instanceof JsonObject && json.get(key) instanceof JsonObject)
                merge(json.getAsJsonObject(key), value.getAsJsonObject());
        }
    }
}

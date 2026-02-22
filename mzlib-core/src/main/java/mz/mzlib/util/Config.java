package mz.mzlib.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@ApiStatus.Experimental
public class Config
{
    public Object data;
    public Config(Object data)
    {
        this.data = data;
    }

    public <T extends @Nullable Object> T get(String path, T def)
    {
        //noinspection unchecked
        T result = (T) this.data;
        for(String key : path.split("\\."))
        {
            //noinspection unchecked
            result = (T) RuntimeUtil.<Map<String, Object>>cast(result).get(key);
            if(result == null)
                return def;
        }
        return result;
    }
    public @Nullable Object get(String path)
    {
        return this.get(path, null);
    }
    public <T extends @Nullable String> T getString(String path, T def)
    {
        return this.get(path, def);
    }
    public @Nullable String getString(String path)
    {
        return this.get(path, null);
    }
    public <T extends @Nullable Number> T getNumber(String path, T def)
    {
        return this.get(path, def);
    }
    public @Nullable Number getNumber(String path)
    {
        return this.get(path, null);
    }
    public boolean getBoolean(String path, boolean def)
    {
        return this.<Boolean>get(path, def);
    }
    public boolean getBoolean(String path)
    {
        return this.getBoolean(path, false);
    }
    public @Nullable List<Object> getList(String path)
    {
        return this.get(path, null);
    }
    public @Nullable List<String> getStringList(String path)
    {
        return this.get(path, null);
    }
    public @Nullable Map<String, Object> getMap(String path)
    {
        return this.get(path, null);
    }

    public static Config loadJson(InputStream def, File file) throws Exception
    {
        Object scope = JsUtil.initScope();
        Object json = JsUtil.toJvm(JsUtil.parseJson(scope, new String(IOUtil.readAll(def), StandardCharsets.UTF_8)));
        if(file.isFile())
        {
            Object cnt;
            try(FileInputStream fis = new FileInputStream(file))
            {
                cnt = JsUtil.toJvm(JsUtil.parseJson(scope, new String(IOUtil.readAll(fis), StandardCharsets.UTF_8)));
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
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            fos.write(gson.toJson(gson.fromJson(JsUtil.toJson(scope, json), JsonElement.class))
                .getBytes(StandardCharsets.UTF_8));
        }
        return new Config(json);
    }
    public static Config loadJs(InputStream def, File file) throws Exception
    {
        return loadJs(JsUtil.initScope(), def, file);
    }
    public static Config loadJs(Object scope, InputStream def, File file) throws Exception
    {
        Object ignored = JsUtil.eval(scope, new String(IOUtil.readAll(def), StandardCharsets.UTF_8));
        if(file.isFile())
        {
            try(FileInputStream fis = new FileInputStream(file))
            {
                JsUtil.eval(scope, new String(IOUtil.readAll(fis), StandardCharsets.UTF_8));
            }
        }
        else
        {
            boolean ignored1 = file.getParentFile().mkdirs();
            boolean ignored2 = file.createNewFile();
        }
        return new Config(JsUtil.toJvm(scope));
    }

    private static void merge(Object data, Object def)
    {
        if(!(def instanceof Map))
            return;
        for(Map.Entry<String, Object> i : RuntimeUtil.<Map<String, Object>>cast(def).entrySet())
        {
            Map<String, Object> d = RuntimeUtil.cast(data);
            if(!d.containsKey(i.getKey()))
                d.put(i.getKey(), i.getValue());
            else if(i.getValue() instanceof Map && d.get(i.getKey()) instanceof Map)
                merge(d.get(i.getKey()), i.getValue());
        }
    }
}

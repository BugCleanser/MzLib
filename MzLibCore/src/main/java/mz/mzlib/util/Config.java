package mz.mzlib.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

public class Config
{
    public Object data;
    public Config(Object data)
    {
        this.data = data;
    }
    
    public Object get(String path, Object def)
    {
        Object result = this.data;
        for(String key: path.split("\\."))
        {
            result = RuntimeUtil.<Map<String, Object>>cast(result).get(key);
            if(result==null)
                return def;
        }
        return result;
    }
    public Object get(String path)
    {
        return this.get(path, null);
    }
    public String getString(String path, String def)
    {
        return (String)this.get(path, def);
    }
    public String getString(String path)
    {
        return this.getString(path, null);
    }
    public Number getNumber(String path, Number def)
    {
        return (Number)this.get(path, def);
    }
    public Number getNumber(String path)
    {
        return this.getNumber(path, null);
    }
    public boolean getBoolean(String path, boolean def)
    {
        return (boolean)this.get(path, def);
    }
    public boolean getBoolean(String path)
    {
        return (boolean)this.get(path, null);
    }
    public List<Object> getList(String path)
    {
        return RuntimeUtil.cast(this.get(path, null));
    }
    public List<String> getStringList(String path)
    {
        return RuntimeUtil.cast(this.getList(path));
    }
    public Map<String, Object> getMap(String path)
    {
        return RuntimeUtil.cast(this.get(path, null));
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
            fos.write(gson.toJson(gson.fromJson(JsUtil.toJson(scope, json), JsonElement.class)).getBytes(StandardCharsets.UTF_8));
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
    @Deprecated
    public static Config load(InputStream def, File file) throws Exception
    {
        return loadJson(def, file);
    }
    
    private static void merge(Object data, Object def)
    {
        if(!(def instanceof Map))
            return;
        for(Map.Entry<String, Object> i: RuntimeUtil.<Map<String, Object>>cast(def).entrySet())
        {
            Map<String, Object> d = RuntimeUtil.cast(data);
            if(!d.containsKey(i.getKey()))
                d.put(i.getKey(), i.getValue());
            else if(i.getValue() instanceof Map && d.get(i.getKey()) instanceof Map)
                merge(d.get(i.getKey()), i.getValue());
        }
    }
}

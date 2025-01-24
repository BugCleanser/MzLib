package mz.mzlib.i18n;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import mz.mzlib.util.IOUtil;
import mz.mzlib.util.MapEntry;
import mz.mzlib.util.RuntimeUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class I18n
{
    public static I18n custom = new I18n(new ConcurrentHashMap<>(), Float.NaN);
    public static String defaultLanguage = "zh_cn";
    
    public Map<String, Map<String, String>> map;
    public float priority;
    
    public I18n(Map<String, Map<String, String>> map, float priority)
    {
        this.map = map;
        this.priority = priority;
    }
    
    public float getPriority()
    {
        return this.priority;
    }
    public String get(String language, String key)
    {
        Map<String, String> result = this.map.get(language);
        if(result==null)
            return null;
        return result.get(key);
    }
    public static String getTranslationDefault(String language, String key)
    {
        for(I18n i: RegistrarI18n.instance.sortedI18ns)
        {
            String result = i.get(language, key);
            if(result!=null)
                return result;
        }
        return null;
    }
    public static String getTranslation(String language, String key, String def)
    {
        String result = custom.get(language, key);
        if(result!=null)
            return result;
        result = getTranslationDefault(language, key);
        if(result!=null)
            return result;
        if(!Objects.equals(language, defaultLanguage))
            return getTranslation(defaultLanguage, key, def);
        return def;
    }
    public static String getTranslation(String language, String key)
    {
        return getTranslation(language, key, key);
    }
    
    
    public static Map<String, String> load(Properties properties)
    {
        return RuntimeUtil.cast(properties);
    }
    
    public static Map<String, String> load(JsonObject json)
    {
        return json.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e->e.getValue().getAsString()));
    }
    
    public static Map.Entry<String, Map<String, String>> load(String fileName, InputStream is) throws IOException
    {
        if(fileName.endsWith(".lang"))
            return new MapEntry<>(fileName.substring(0, fileName.length()-".lang".length()), load(IOUtil.readProperties(is)));
        else if(fileName.endsWith(".json"))
            return new MapEntry<>(fileName.substring(0, fileName.length()-".json".length()), load(new Gson().fromJson(new InputStreamReader(is, StandardCharsets.UTF_8), JsonObject.class)));
        else
            return null;
    }
    
    public static I18n load(File folder, float priority) throws IOException
    {
        Map<String, Map<String, String>> map = new HashMap<>();
        for(File file: Objects.requireNonNull(folder.listFiles()))
        {
            try(InputStream is = Files.newInputStream(file.toPath()))
            {
                Map.Entry<String, Map<String, String>> result = load(file.getName(), is);
                if(result!=null)
                    map.put(result.getKey(), result.getValue());
            }
        }
        return new I18n(map, priority);
    }
    
    public static I18n load(File fileZip, String path, float priority) throws IOException
    {
        try(ZipFile zip = new ZipFile(fileZip))
        {
            if(!path.endsWith("/"))
                path += '/';
            Map<String, Map<String, String>> map = new HashMap<>();
            for(Enumeration<? extends ZipEntry> i = zip.entries(); i.hasMoreElements(); )
            {
                ZipEntry e = i.nextElement();
                if(e.getName().startsWith(path))
                {
                    try(InputStream is = zip.getInputStream(e))
                    {
                        Map.Entry<String, Map<String, String>> result = load(e.getName().substring(path.length()), is);
                        if(result!=null)
                            map.put(result.getKey(), result.getValue());
                    }
                }
            }
            return new I18n(map, priority);
        }
    }
}

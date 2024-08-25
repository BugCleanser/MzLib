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
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class I18n
{
    public Map<String, Map<String, String>> map;
    public float priority;
    public I18n(Map<String, Map<String, String>> map, float priority)
    {
        this.map=map;
        this.priority=priority;
    }

    public float getPriority()
    {
        return this.priority;
    }
    public String getTranslation(String language,String key)
    {
        Map<String, String> result = this.map.get(language);
        if(result==null)
            return null;
        return result.get(key);
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
        if (fileName.endsWith(".lang"))
            return new MapEntry<>(fileName.substring(0, fileName.length() - ".lang".length()), load(IOUtil.readProperties(is)));
        else if(fileName.endsWith(".json"))
            return new MapEntry<>(fileName.substring(0, fileName.length() - ".json".length()), load(new Gson().fromJson(new InputStreamReader(is),JsonObject.class)));
        else
            return null;
    }
    public static I18n load(File folder, float priority) throws IOException
    {
        Map<String, Map<String, String>> map=new HashMap<>();
        for(File file: Objects.requireNonNull(folder.listFiles()))
        {
            try(InputStream is= Files.newInputStream(file.toPath()))
            {
                Map.Entry<String, Map<String, String>> result = load(file.getName(), is);
                if(result!=null)
                    map.put(result.getKey(), result.getValue());
            }
        }
        return new I18n(map, priority);
    }
    public static I18n load(ZipFile zip, String path, float priority) throws IOException
    {
        if(!path.endsWith("/"))
            path+='/';
        Map<String, Map<String, String>> map=new HashMap<>();
        for(Enumeration<? extends ZipEntry> i = zip.entries();i.hasMoreElements();)
        {
            ZipEntry e = i.nextElement();
            if(e.getName().startsWith(path))
            {
                try(InputStream is=zip.getInputStream(e))
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

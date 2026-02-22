package mz.mzlib.i18n;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import mz.mzlib.util.*;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

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

    @ApiStatus.Internal
    public Map<String, Map<String, String>> map;
    float priority;

    public I18n(Map<String, Map<String, String>> map, float priority)
    {
        this.map = map;
        this.priority = priority;
    }

    public float getPriority()
    {
        return this.priority;
    }
    public @Nullable String get(String language, String key)
    {
        @Nullable Map<String, String> result = this.map.get(language);
        if(result == null)
            return null;
        return result.get(key);
    }
    public static String getDefaultLanguage()
    {
        return defaultLanguage;
    }
    public static @Nullable String getDefaultSource(String language, String key)
    {
        language = language.toLowerCase();
        for(I18n i : RegistrarI18n.instance.sortedI18ns)
        {
            String result = i.get(language, key);
            if(result != null)
                return result;
        }
        return null;
    }
    @Deprecated
    public static @Nullable String getTranslationDefault(String language, String key)
    {
        return getDefaultSource(language, key);
    }
    public static <T extends @Nullable String> T getSource(String language, String key, T def)
    {
        language = language.toLowerCase();
        //noinspection unchecked
        T result = (T) custom.get(language, key);
        if(result != null)
            return result;
        //noinspection unchecked
        result = (T) getDefaultSource(language, key);
        if(result != null)
            return result;
        if(!Objects.equals(language, defaultLanguage.toLowerCase()))
            return getSource(defaultLanguage, key, def);
        return def;
    }
    @Deprecated
    public static String getTranslation(String language, String key, String def)
    {
        return getSource(language, key, def);
    }
    @Deprecated
    public static String getTranslation(String language, String key)
    {
        return getSource(language, key, key);
    }

    public static Object scopeDefault = JsUtil.initSafeScope();
    public static String resolve(JsUtil.Settings settings, Object scope, String language, String key, Object args)
    {
        String translation = getSource(language, key, null);
        if(translation == null)
            return key + args;
        try
        {
            return JsUtil.eval(settings, JsUtil.wrap(scope, args), "`" + translation + "`").toString();
        }
        catch(Exception e)
        {
            return "[Error]" + key + ":" + e.getMessage();
        }
    }
    public static String resolve(String language, String key, Object args)
    {
        return resolve(JsUtil.Settings.def, scopeDefault, language, key, args);
    }
    public static String resolve(String language, String key)
    {
        return resolve(language, key, Collections.emptyMap());
    }
    @Deprecated
    public static String getTranslationWithArgs(String language, String key, Map<String, Object> args)
    {
        return resolve(language, key, args);
    }

    public static Map<String, String> load(Properties properties)
    {
        return RuntimeUtil.cast(properties);
    }

    public static Map<String, String> load(JsonObject json)
    {
        return json.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().getAsString()));
    }

    @ApiStatus.Internal
    public static @Nullable Pair<String, Map<String, String>> load(String fileName, InputStream is) throws IOException
    {
        fileName = fileName.toLowerCase();
        if(fileName.endsWith(".lang"))
            return Pair.of(
                fileName.substring(0, fileName.length() - ".lang".length()), load(IOUtil.readProperties(is)));
        else if(fileName.endsWith(".json"))
            return Pair.of(
                fileName.substring(0, fileName.length() - ".json".length()),
                load(new Gson().fromJson(new InputStreamReader(is, StandardCharsets.UTF_8), JsonObject.class))
            );
        else
            return null;
    }

    public static I18n load(File folder, float priority) throws IOException
    {
        Map<String, Map<String, String>> map = new HashMap<>();
        for(File file : Objects.requireNonNull(folder.listFiles()))
        {
            try(InputStream is = Files.newInputStream(file.toPath()))
            {
                Pair<String, Map<String, String>> result = load(file.getName(), is);
                if(result != null)
                    map.put(result.getFirst(), result.getSecond());
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
                        Pair<String, Map<String, String>> result = load(e.getName().substring(path.length()), is);
                        if(result != null)
                            map.put(result.getFirst(), result.getSecond());
                    }
                }
            }
            return new I18n(map, priority);
        }
    }
}

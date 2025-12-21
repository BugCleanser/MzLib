package mz.mzlib.minecraft.i18n;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import mz.mzlib.Priority;
import mz.mzlib.i18n.I18n;
import mz.mzlib.minecraft.*;
import mz.mzlib.minecraft.command.CommandSource;
import mz.mzlib.minecraft.entity.player.EntityPlayer;
import mz.mzlib.minecraft.text.Text;
import mz.mzlib.module.MzModule;
import mz.mzlib.util.*;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MinecraftI18n extends MzModule
{
    public static MinecraftI18n instance = new MinecraftI18n();

    public I18n i18nMinecraft = new I18n(new HashMap<>(), Priority.VERY_VERY_LOW);
    public CompletableFuture<Void> taskLoading;
    public void loadMinecraftLanguages()
    {
        if(this.taskLoading != null && !this.taskLoading.isDone())
            return;
        this.taskLoading = CompletableFuture.runAsync(() ->
        {
            try
            {
                MinecraftServer.instance.sendMessage(
                    MinecraftI18n.resolveText(I18n.getDefaultLanguage(), "mzlib.lang.minecraft.load"));
                String folder = "minecraft/lang/";
                Map<String, CompletableFuture<byte[]>> tasks = new HashMap<>();
                for(String file : AssetsHelp.instance)
                {
                    if(file.startsWith(folder))
                    {
                        String fileName = file.substring(folder.length());
                        tasks.put(
                            fileName, CompletableFuture.supplyAsync(
                                ThrowableSupplier.of(() -> AssetsHelp.instance.getAsset(file)))
                        );
                    }
                }
                Map<String, Map<String, String>> map = new HashMap<>();
                for(Map.Entry<String, CompletableFuture<byte[]>> task : tasks.entrySet())
                {
                    Map.Entry<String, Map<String, String>> result = I18n.load(
                        task.getKey(), new ByteArrayInputStream(task.getValue().get()));
                    if(result != null)
                        map.put(result.getKey(), result.getValue());
                }
                this.i18nMinecraft.map = map;
                MinecraftServer.instance.sendMessage(
                    MinecraftI18n.resolveText(I18n.getDefaultLanguage(), "mzlib.lang.minecraft.load.success"));
            }
            catch(Throwable e)
            {
                e.printStackTrace(System.err);
                MinecraftServer.instance.sendMessage(
                    MinecraftI18n.resolveText(I18n.getDefaultLanguage(), "mzlib.lang.minecraft.load.failure"));
            }
            this.taskLoading = null;
        });
    }

    public static String getLanguage(CommandSource commandSource)
    {
        for(EntityPlayer player : commandSource.getPlayer())
        {
            return player.getLanguage();
        }
        return I18n.getDefaultLanguage();
    }

    public static String resolve(CommandSource commandSource, String key)
    {
        return I18n.resolve(getLanguage(commandSource), key);
    }
    public static String resolve(EntityPlayer player, String key)
    {
        return I18n.resolve(player.getLanguage(), key);
    }

    public static String resolve(CommandSource commandSource, String key, Object args)
    {
        return I18n.resolve(getLanguage(commandSource), key, args);
    }
    public static String resolve(EntityPlayer player, String key, Object args)
    {
        return I18n.resolve(player.getLanguage(), key, args);
    }

    static Random random = new Random();
    static Object scope = JsUtil.newObject(I18n.scopeDefault);

    static
    {
        MinecraftJsUtil.initText(scope);
        MinecraftJsUtil.initItem(scope);
        MinecraftJsUtil.initNbt(scope);
    }

    public static Text resolveText(String language, String key, Object args)
    {
        return resolveTexts(language, key, args, Collections::singletonList).get(0);
    }
    public static List<Text> resolveTexts(String language, String key, Object args)
    {
        return resolveTexts(language, key, args, str -> Arrays.asList(str.split("\\R")));
    }
    public static List<Text> resolveTexts(String language, String key, Object args, Function<String, ? extends List<String>> splitter)
    {
        StringBuilder sb = new StringBuilder();
        long l = random.nextLong();
        sb.append((char) ((l >>> 48) & 0xFFFF));
        sb.append((char) ((l >>> 32) & 0xFFFF));
        sb.append((char) ((l >>> 16) & 0xFFFF));
        sb.append((char) (l & 0xFFFF));
        sb.append((char) 0);
        JsUtil.Settings settings = new JsUtil.Settings();
        Map<String, Text> map = new HashMap<>();
        settings.proxies.put(
            Text.class, JsUtil.mapToObject(
                settings, scope, Collections.singletonMap(
                    "toString", JsUtil.function((s, scope, thisObj, as) ->
                    {
                        sb.setCharAt(4, (char) (sb.charAt(4) - 1));
                        String result = sb.toString();
                        map.put(result, (Text) JsUtil.toJvm(thisObj));
                        return result;
                    })
                )
            )
        );
        return splitter.apply(I18n.resolve(settings, scope, language, key, args)).stream().map(splitting ->
        {
            List<Object> result = CollectionUtil.newArrayList(splitting);
            for(Map.Entry<String, Text> e : map.entrySet())
            {
                result = result.stream().flatMap(o ->
                {
                    if(o instanceof Text)
                        return Stream.of((Text) o);
                    return Stream.of(CollectionUtil.replace(o.toString(), e.getKey(), e.getValue()).toArray());
                }).collect(Collectors.toList());
            }
            for(Ref<Object> i : CollectionUtil.each(result))
            {
                if(i.get() instanceof Text)
                    continue;
                i.set(Text.literal(i.get().toString()));
            }
            if(result.isEmpty())
                return Text.literal("");
            else if(result.size() == 1)
                return (Text) result.get(0);
            else
                return Text.literal("").setExtra(RuntimeUtil.cast(result));
        }).collect(Collectors.toList());
    }
    public static Text resolveText(CommandSource commandSource, String key, Object args)
    {
        return resolveText(getLanguage(commandSource), key, args);
    }
    public static Text resolveText(EntityPlayer player, String key, Object args)
    {
        return resolveText(player.getLanguage(), key, args);
    }
    public static Text resolveText(String language, String key)
    {
        return resolveText(language, key, Collections.emptyMap());
    }
    public static Text resolveText(CommandSource commandSource, String key)
    {
        return resolveText(getLanguage(commandSource), key);
    }
    public static Text resolveText(EntityPlayer player, String key)
    {
        return resolveText(player.getLanguage(), key);
    }

    public static List<Text> resolveTexts(CommandSource commandSource, String key, Object args)
    {
        return resolveTexts(getLanguage(commandSource), key, args);
    }
    public static List<Text> resolveTexts(EntityPlayer player, String key, Object args)
    {
        return resolveTexts(player.getLanguage(), key, args);
    }
    public static List<Text> resolveTexts(String language, String key)
    {
        return resolveTexts(language, key, Collections.emptyMap());
    }
    public static List<Text> resolveTexts(CommandSource commandSource, String key)
    {
        return resolveTexts(getLanguage(commandSource), key);
    }
    public static List<Text> resolveTexts(EntityPlayer player, String key)
    {
        return resolveTexts(player.getLanguage(), key);
    }

    public static void saveCustomLanguage(String lang)
    {
        try
        {
            File dir = new File(MinecraftPlatform.instance.getMzLibDataFolder(), "lang");
            boolean ignored = dir.mkdirs();
            Files.write(
                new File(dir, lang + ".json").toPath(),
                new GsonBuilder().setPrettyPrinting().create().toJson(I18n.custom.map.get(lang))
                    .getBytes(StandardCharsets.UTF_8)
            );
        }
        catch(IOException e)
        {
            throw RuntimeUtil.sneakilyThrow(e);
        }
    }

    public static void loadCustomLanguages()
    {
        try
        {
            File dir = new File(MinecraftPlatform.instance.getMzLibDataFolder(), "lang");
            boolean ignored = dir.mkdirs();
            for(String file : Objects.requireNonNull(dir.list()))
            {
                if(!file.toLowerCase().endsWith(".json"))
                    continue;
                I18n.custom.map.put(
                    file.substring(0, file.length() - ".json".length()), new ConcurrentHashMap<>(I18n.load(
                        new Gson().fromJson(
                            new String(Files.readAllBytes(new File(dir, file).toPath()), StandardCharsets.UTF_8),
                            JsonObject.class
                        )))
                );
            }
        }
        catch(IOException e)
        {
            throw RuntimeUtil.sneakilyThrow(e);
        }
    }

    public void onLoad()
    {
        I18n.defaultLanguage = MzLibMinecraft.instance.config.getString("default_language").toLowerCase();
        if(MinecraftPlatform.instance.getVersion() < 1300)
            this.register(VanillaI18nV_1300.class);
        this.register(this.i18nMinecraft);
        loadCustomLanguages();
        loadMinecraftLanguages();
    }
}

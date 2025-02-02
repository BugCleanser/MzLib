package mz.mzlib.minecraft.i18n;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import mz.mzlib.Priority;
import mz.mzlib.i18n.I18n;
import mz.mzlib.minecraft.AssetsHelp;
import mz.mzlib.minecraft.MinecraftPlatform;
import mz.mzlib.minecraft.MinecraftServer;
import mz.mzlib.minecraft.MzLibMinecraft;
import mz.mzlib.minecraft.command.CommandSource;
import mz.mzlib.minecraft.entity.player.EntityPlayer;
import mz.mzlib.minecraft.text.Text;
import mz.mzlib.module.MzModule;
import mz.mzlib.util.RuntimeUtil;
import mz.mzlib.util.ThrowableSupplier;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

public class I18nMinecraft extends MzModule
{
    public static I18nMinecraft instance = new I18nMinecraft();
    
    public I18n i18nMinecraft = new I18n(new HashMap<>(), Priority.VERY_VERY_LOW);
    public CompletableFuture<Void> taskLoading;
    public void loadMinecraftLanguages()
    {
        if(this.taskLoading!=null && !this.taskLoading.isDone())
            return;
        this.taskLoading = CompletableFuture.runAsync(()->
        {
            try
            {
                MinecraftServer.instance.sendMessage(Text.literal(I18n.getTranslation(getDefaultLanguage(), "mzlib.lang.minecraft.load")));
                String folder = "minecraft/lang/";
                Map<String, CompletableFuture<byte[]>> tasks = new HashMap<>();
                for(String file: AssetsHelp.instance)
                {
                    if(file.startsWith(folder))
                    {
                        String fileName = file.substring(folder.length());
                        tasks.put(fileName, CompletableFuture.supplyAsync((ThrowableSupplier<byte[], IOException>)()->AssetsHelp.instance.getAsset(file)));
                    }
                }
                Map<String, Map<String, String>> map = new HashMap<>();
                for(Map.Entry<String, CompletableFuture<byte[]>> task: tasks.entrySet())
                {
                    Map.Entry<String, Map<String, String>> result = I18n.load(task.getKey(), new ByteArrayInputStream(task.getValue().get()));
                    if(result!=null)
                        map.put(result.getKey(), result.getValue());
                }
                this.i18nMinecraft.map = map;
                MinecraftServer.instance.sendMessage(Text.literal(I18n.getTranslation(getDefaultLanguage(), "mzlib.lang.minecraft.load.success")));
            }
            catch(Throwable e)
            {
                e.printStackTrace(System.err);
                MinecraftServer.instance.sendMessage(Text.literal(I18n.getTranslation(getDefaultLanguage(), "mzlib.lang.minecraft.load.failure")));
            }
            this.taskLoading = null;
        });
    }
    
    public static String getDefaultLanguage()
    {
        return MzLibMinecraft.instance.config.getString("default_language");
    }
    public static String getTranslation(CommandSource commandSource, String key)
    {
        EntityPlayer player = commandSource.getPlayer();
        if(player.isPresent())
            return getTranslation(player, key);
        return I18n.getTranslation(getDefaultLanguage(), key);
    }
    public static String getTranslation(EntityPlayer player, String key)
    {
        return I18n.getTranslation(player.getLanguage(), key);
    }
    
    public static String getTranslationWithArgs(CommandSource commandSource, String key, Map<String, Object> args)
    {
        EntityPlayer player = commandSource.getPlayer();
        if(player.isPresent())
            return getTranslationWithArgs(player, key, args);
        return I18n.getTranslationWithArgs(getDefaultLanguage(), key, args);
    }
    public static String getTranslationWithArgs(EntityPlayer player, String key, Map<String, Object> args)
    {
        return I18n.getTranslationWithArgs(player.getLanguage(), key, args);
    }
    
    public static void saveCustomLanguage(String lang)
    {
        try
        {
            File dir = new File(MinecraftPlatform.instance.getMzLibDataFolder(), "lang");
            boolean ignored = dir.mkdirs();
            Files.write(new File(dir, lang+".json").toPath(), new GsonBuilder().setPrettyPrinting().create().toJson(I18n.custom.map.get(lang)).getBytes(StandardCharsets.UTF_8));
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
            for(String file: Objects.requireNonNull(dir.list()))
            {
                if(!file.toLowerCase().endsWith(".json"))
                    continue;
                I18n.custom.map.put(file.substring(0, file.length()-".json".length()), new ConcurrentHashMap<>(I18n.load(new Gson().fromJson(new String(Files.readAllBytes(new File(dir, file).toPath()), StandardCharsets.UTF_8), JsonObject.class))));
            }
        }
        catch(IOException e)
        {
            throw RuntimeUtil.sneakilyThrow(e);
        }
    }
    
    public void onLoad()
    {
        if(MinecraftPlatform.instance.getVersion()<1300)
            this.register(VanillaI18nV_1300.class);
        this.register(this.i18nMinecraft);
        loadCustomLanguages();
        loadMinecraftLanguages();
    }
}

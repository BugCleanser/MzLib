package mz.mzlib.minecraft.i18n;

import mz.mzlib.Priority;
import mz.mzlib.i18n.I18n;
import mz.mzlib.minecraft.AssetsHelp;
import mz.mzlib.minecraft.MinecraftPlatform;
import mz.mzlib.module.MzModule;
import mz.mzlib.util.ThrowableSupplier;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class I18nMinecraft extends MzModule
{
    public static I18nMinecraft instance = new I18nMinecraft();
    
    public I18n i18n = new I18n(new HashMap<>(), Priority.VERY_VERY_LOW);
    public CompletableFuture<Void> taskLoading;
    public void loadLanguages()
    {
        if(this.taskLoading!=null && !this.taskLoading.isDone())
            return;
        this.taskLoading = CompletableFuture.runAsync(()->
        {
            try
            {
                MinecraftPlatform.instance.getMzLibLogger().info("正在加载MC语言文件");
                String folder = "minecraft/lang/";
                Map<String, CompletableFuture<byte[]>> tasks = new HashMap<>();
                for(String file: AssetsHelp.instance)
                {
                    if(file.startsWith(folder))
                    {
                        String fileName = file.substring(folder.length());
                        tasks.put(fileName.substring(0, fileName.lastIndexOf('.')), CompletableFuture.supplyAsync((ThrowableSupplier<byte[], IOException>)()->AssetsHelp.instance.getAsset(file)));
                    }
                }
                Map<String, Map<String, String>> map=new HashMap<>();
                for(Map.Entry<String, CompletableFuture<byte[]>> task: tasks.entrySet())
                {
                    Map.Entry<String, Map<String, String>> result = I18n.load(task.getKey(), new ByteArrayInputStream(task.getValue().get()));
                    if(result!=null)
                        map.put(result.getKey(), result.getValue());
                }
                i18n.map=map;
                MinecraftPlatform.instance.getMzLibLogger().info("MC语言文件加载完成");
            }
            catch(Throwable e)
            {
                e.printStackTrace(System.err);
                MinecraftPlatform.instance.getMzLibLogger().warning("MC语言文件加载失败，请稍后重试");
            }
            this.taskLoading = null;
        });
    }
    
    public void onLoad()
    {
        this.register(this.i18n);
        loadLanguages();
    }
}

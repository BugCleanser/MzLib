package mz.mzlib.minecraft.I18n;

import mz.mzlib.i18n.I18n;
import mz.mzlib.minecraft.AssetsHelp;
import mz.mzlib.module.MzModule;
import mz.mzlib.util.RuntimeUtil;
import mz.mzlib.util.ThrowableSupplier;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class I18nMinecraft extends MzModule
{
    public static I18nMinecraft instance=new I18nMinecraft();

    public void onLoad()
    {
        try
        {
            String folder = "minecraft/lang/";
            Map<String, Map<String, String>> map = new HashMap<>();
            Map<String,CompletableFuture<byte[]>> tasks=new HashMap<>();
            for (String file : AssetsHelp.instance)
            {
                if (file.startsWith(folder))
                {
                    String fileName = file.substring(folder.length());
                    tasks.put(fileName.substring(0,fileName.lastIndexOf('.')),CompletableFuture.supplyAsync((ThrowableSupplier<byte[], IOException>)()->AssetsHelp.instance.getAsset(file)));
                }
            }
            for(Map.Entry<String,CompletableFuture<byte[]>> task:tasks.entrySet())
            {
                Map.Entry<String, Map<String, String>> result = I18n.load(task.getKey(), new ByteArrayInputStream(task.getValue().get()));
                if (result != null)
                    map.put(result.getKey(), result.getValue());
            }
            this.register(new I18n(map, -1));
        }
        catch (Throwable e)
        {
            throw RuntimeUtil.sneakilyThrow(e);
        }
    }
}

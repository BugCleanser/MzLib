package mz.mzlib.minecraft;

import mz.mzlib.i18n.I18n;
import mz.mzlib.module.MzModule;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class I18nMinecraft extends MzModule
{
    public static I18nMinecraft instance=new I18nMinecraft();

    public void onLoad()
    {
        String folder="minecraft/lang/";
        Map<String, Map<String, String>> map = new HashMap<>();
        for (String file : AssetsHelp.instance)
        {
            if(file.startsWith(folder))
            {
                Map.Entry<String, Map<String, String>> result = I18n.load(file.substring(folder.length()), new ByteArrayInputStream(AssetsHelp.instance.getAsset(file)));
                if(result!=null)
                    map.put(result.getKey(), result.getValue());
            }
        }
        this.register(new I18n(map, -1));
    }
}

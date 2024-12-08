package mz.mzlib.i18n;

import java.util.concurrent.ConcurrentHashMap;

public class I18nCustom extends I18n
{
    public static I18nCustom instance=new I18nCustom();
    public I18nCustom()
    {
        super(new ConcurrentHashMap<>(), Float.NaN);
    }
    
    
}

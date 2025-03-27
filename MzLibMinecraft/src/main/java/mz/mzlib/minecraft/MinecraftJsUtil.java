package mz.mzlib.minecraft;

import mz.mzlib.minecraft.text.Text;
import mz.mzlib.minecraft.text.TextColor;
import mz.mzlib.util.JsUtil;

public class MinecraftJsUtil
{
    public static Object initScope()
    {
        return initScope(JsUtil.initScope());
    }
    public static Object initScope(Object scope)
    {
        initText(scope);
        return scope;
    }
    
    public static void initText(Object scope)
    {
        JsUtil.put(scope, "Text", JsUtil.wrapClass(scope, Text.class));
        JsUtil.put(scope, "TextColor", JsUtil.wrapClass(scope, TextColor.class));
    }
}

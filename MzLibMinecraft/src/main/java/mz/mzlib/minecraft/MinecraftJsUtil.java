package mz.mzlib.minecraft;

import mz.mzlib.minecraft.item.Item;
import mz.mzlib.minecraft.item.ItemStack;
import mz.mzlib.minecraft.item.ItemStackBuilder;
import mz.mzlib.minecraft.nbt.*;
import mz.mzlib.minecraft.text.Text;
import mz.mzlib.minecraft.text.TextClickEvent;
import mz.mzlib.minecraft.text.TextColor;
import mz.mzlib.minecraft.text.TextHoverEvent;
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
        initItem(scope);
        initNbt(scope);
        return scope;
    }
    
    public static void initText(Object scope)
    {
        JsUtil.put(scope, "Text", JsUtil.wrapClass(scope, Text.class));
        JsUtil.put(scope, "TextColor", JsUtil.wrapClass(scope, TextColor.class));
        JsUtil.put(scope, "TextHoverEvent", JsUtil.wrapClass(scope, TextHoverEvent.class));
        JsUtil.put(scope, "TextClickEvent", JsUtil.wrapClass(scope, TextClickEvent.class));
    }
    
    public static void initItem(Object scope)
    {
        JsUtil.put(scope, "Item", JsUtil.wrapClass(scope, Item.class));
        JsUtil.put(scope, "ItemStack", JsUtil.wrapClass(scope, ItemStack.class));
        JsUtil.put(scope, "ItemStackBuilder", JsUtil.wrapClass(scope, ItemStackBuilder.class));
    }
    
    public static void initNbt(Object scope)
    {
        JsUtil.put(scope, "NbtElement", JsUtil.wrapClass(scope, NbtElement.class));
        JsUtil.put(scope, "NbtCompound", JsUtil.wrapClass(scope, NbtCompound.class));
        JsUtil.put(scope, "NbtByte", JsUtil.wrapClass(scope, NbtByte.class));
        JsUtil.put(scope, "NbtDouble", JsUtil.wrapClass(scope, NbtDouble.class));
        JsUtil.put(scope, "NbtFloat", JsUtil.wrapClass(scope, NbtFloat.class));
        JsUtil.put(scope, "NbtInt", JsUtil.wrapClass(scope, NbtInt.class));
        JsUtil.put(scope, "NbtIntArray", JsUtil.wrapClass(scope, NbtIntArray.class));
        JsUtil.put(scope, "NbtList", JsUtil.wrapClass(scope, NbtList.class));
        JsUtil.put(scope, "NbtLong", JsUtil.wrapClass(scope, NbtLong.class));
        JsUtil.put(scope, "NbtLongArrayV1200", JsUtil.wrapClass(scope, NbtLongArrayV1200.class));
        JsUtil.put(scope, "NbtShort", JsUtil.wrapClass(scope, NbtShort.class));
        JsUtil.put(scope, "NbtString", JsUtil.wrapClass(scope, NbtString.class));
        JsUtil.put(scope, "NbtUtil", JsUtil.wrapClass(scope, NbtUtil.class));
    }
}

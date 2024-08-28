package mz.mzlib.minecraft.text;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.WrapConstructor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(
        {
                @VersionName(name = "net.minecraft.text.SelectorText", end = 1400),
                @VersionName(name = "net.minecraft.network.chat.SelectorComponent", begin = 1400, end = 1403),
                @VersionName(name = "net.minecraft.text.SelectorText", begin = 1403, end = 1900)
        })
public interface TextSelectorV_1900 extends WrapperObject, AbstractTextV_1900
{
    @WrapperCreator
    static TextSelectorV_1900 create(Object wrapped)
    {
        return WrapperObject.create(TextSelectorV_1900.class, wrapped);
    }

    @WrapConstructor
    TextSelectorV_1900 staticNewInstance(String selector);
    static TextSelectorV_1900 newInstance(String selector)
    {
        return create(null).staticNewInstance(selector);
    }

    @WrapMinecraftMethod(@VersionName(name="getPattern"))
    String getSelector();
}

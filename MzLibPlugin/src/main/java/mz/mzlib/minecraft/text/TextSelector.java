package mz.mzlib.minecraft.text;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.WrapConstructor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(@VersionName(name="net.minecraft.text.SelectorText"))
public interface TextSelector extends WrapperObject, AbstractText
{
    @WrapperCreator
    static TextSelector create(Object wrapped)
    {
        return WrapperObject.create(TextSelector.class, wrapped);
    }

    @WrapConstructor
    TextSelector staticNewInstance(String selector);
    static TextSelector newInstance(String selector)
    {
        return create(null).staticNewInstance(selector);
    }

    @WrapMinecraftMethod(@VersionName(name="getPattern"))
    String getSelector();
}

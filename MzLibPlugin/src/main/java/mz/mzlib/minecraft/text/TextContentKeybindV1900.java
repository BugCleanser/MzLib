package mz.mzlib.minecraft.text;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.WrapConstructor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(@VersionName(name="net.minecraft.text.KeybindTextContent", begin = 1900))
public interface TextContentKeybindV1900 extends WrapperObject, TextContentV1900
{
    @WrapperCreator
    static TextContentKeybindV1900 create(Object wrapped)
    {
        return WrapperObject.create(TextContentKeybindV1900.class, wrapped);
    }

    @WrapConstructor
    TextContentKeybindV1900 staticNewInstance(String keybind);
    static TextContentKeybindV1900 newInstance(String keybind)
    {
        return create(null).staticNewInstance(keybind);
    }

    @WrapMinecraftMethod(@VersionName(name="getKey"))
    String getKeybind();
}

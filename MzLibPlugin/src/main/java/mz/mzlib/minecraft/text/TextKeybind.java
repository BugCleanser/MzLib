package mz.mzlib.minecraft.text;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.WrapConstructor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(@VersionName(name="net.minecraft.util.KeyBindComponent"))
public interface TextKeybind extends WrapperObject, AbstractText
{
    @WrapperCreator
    static TextKeybind create(Object wrapped)
    {
        return WrapperObject.create(TextKeybind.class, wrapped);
    }

    @WrapConstructor
    TextKeybind staticNewInstance(String keybind);
    static TextKeybind newInstance(String keybind)
    {
        return create(null).staticNewInstance(keybind);
    }

    @WrapMinecraftMethod(@VersionName(name="getKeybind"))
    String getKeybind();
}

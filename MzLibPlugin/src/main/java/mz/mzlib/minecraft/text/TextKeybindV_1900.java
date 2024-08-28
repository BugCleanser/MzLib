package mz.mzlib.minecraft.text;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.WrapConstructor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(
        {
                @VersionName(name = "net.minecraft.util.KeyBindComponent", end=1403),
                @VersionName(name = "net.minecraft.text.KeybindText", begin=1403, end=1900)
        })
public interface TextKeybindV_1900 extends WrapperObject, AbstractTextV_1900
{
    @WrapperCreator
    static TextKeybindV_1900 create(Object wrapped)
    {
        return WrapperObject.create(TextKeybindV_1900.class, wrapped);
    }

    @WrapConstructor
    TextKeybindV_1900 staticNewInstance(String keybind);
    static TextKeybindV_1900 newInstance(String keybind)
    {
        return create(null).staticNewInstance(keybind);
    }

    @WrapMinecraftMethod(@VersionName(name="getKeybind"))
    String getKeybind();
}

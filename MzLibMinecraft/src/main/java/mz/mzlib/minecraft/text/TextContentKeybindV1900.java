package mz.mzlib.minecraft.text;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.WrapConstructor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@VersionRange(begin=1900)
@WrapMinecraftClass(@VersionName(name="net.minecraft.text.KeybindTextContent"))
public interface TextContentKeybindV1900 extends WrapperObject, TextContentV1900
{
    WrapperFactory<TextContentKeybindV1900> FACTORY = WrapperFactory.of(TextContentKeybindV1900.class);
    @Deprecated
    @WrapperCreator
    static TextContentKeybindV1900 create(Object wrapped)
    {
        return WrapperObject.create(TextContentKeybindV1900.class, wrapped);
    }

    @WrapConstructor
    TextContentKeybindV1900 static$newInstance(String keybind);
    static TextContentKeybindV1900 newInstance(String keybind)
    {
        return FACTORY.getStatic().static$newInstance(keybind);
    }

    @WrapMinecraftMethod(@VersionName(name="getKey"))
    String getKey();
}

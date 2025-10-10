package mz.mzlib.minecraft.text;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.WrapConstructor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@VersionRange(begin=1200, end=1900)
@WrapMinecraftClass(
        {
                @VersionName(name = "net.minecraft.util.KeyBindComponent", end=1403),
                @VersionName(name = "net.minecraft.text.KeybindText", begin=1403)
        })
public interface TextKeybindV1200_1900 extends WrapperObject, AbstractTextV_1900
{
    WrapperFactory<TextKeybindV1200_1900> FACTORY = WrapperFactory.of(TextKeybindV1200_1900.class);
    @Deprecated
    @WrapperCreator
    static TextKeybindV1200_1900 create(Object wrapped)
    {
        return WrapperObject.create(TextKeybindV1200_1900.class, wrapped);
    }

    @WrapConstructor
    TextKeybindV1200_1900 staticNewInstance(String keybind);
    static TextKeybindV1200_1900 newInstance(String keybind)
    {
        return create(null).staticNewInstance(keybind);
    }

    @WrapMinecraftMethod({@VersionName(name="getKeybind", end=1400), @VersionName(name="getKey", begin=1400)})
    String getKey();
}

package mz.mzlib.minecraft.text;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.*;

@VersionRange(begin=1200)
@WrapMinecraftClass(
        {
                @VersionName(name="net.minecraft.util.KeyBindComponent", end=1400),
                @VersionName(name="net.minecraft.text.KeybindText", begin=1400, end=1900),
                @VersionName(name="net.minecraft.text.Text", begin=1900)
        })
public interface TextKeybindV1200 extends WrapperObject, Text
{
    WrapperFactory<TextKeybindV1200> FACTORY = WrapperFactory.of(TextKeybindV1200.class);
    
    static TextKeybindV1200 newInstance(String key)
    {
        return FACTORY.getStatic().static$newInstance(key);
    }
    TextKeybindV1200 static$newInstance(String key);
    @SpecificImpl("static$newInstance")
    @VersionRange(end=1900)
    @WrapConstructor
    TextKeybindV1200 static$newInstanceV_1900(String key);
    @SpecificImpl("static$newInstance")
    @VersionRange(begin=1900)
    default TextKeybindV1200 static$newInstanceV1900(String key)
    {
        return TextMutableV1600.newInstanceV1900(TextContentKeybindV1900.newInstance(key)).castTo(FACTORY);
    }
    
    String getKey();
    @SpecificImpl("getKey")
    @VersionRange(end=1900)
    @WrapMinecraftMethod({@VersionName(name="getKeybind", end=1400), @VersionName(name="getKey", begin=1400)})
    String getKeyV_1900();
    @SpecificImpl("getKey")
    @VersionRange(begin=1900)
    default String getKeyV1900()
    {
        return this.getContentV1900().castTo(TextContentKeybindV1900.FACTORY).getKey();
    }
}

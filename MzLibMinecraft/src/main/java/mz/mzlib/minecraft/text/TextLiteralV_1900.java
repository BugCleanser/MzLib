package mz.mzlib.minecraft.text;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.*;

@WrapMinecraftClass(
        {
                @VersionName(name = "net.minecraft.text.LiteralText", end=1400),
                @VersionName(name = "net.minecraft.network.chat.TextComponent", begin=1400, end=1403),
                @VersionName(name = "net.minecraft.text.LiteralText", begin=1403, end=1900)
        })
public interface TextLiteralV_1900 extends WrapperObject, AbstractTextV_1900
{
    WrapperFactory<TextLiteralV_1900> FACTORY = WrapperFactory.of(TextLiteralV_1900.class);
    @Deprecated
    @WrapperCreator
    static TextLiteralV_1900 create(Object wrapped)
    {
        return WrapperObject.create(TextLiteralV_1900.class, wrapped);
    }

    @WrapConstructor
    TextLiteralV_1900 staticNewInstance(String literal);
    static TextLiteralV_1900 newInstance(String literal)
    {
        return create(null).staticNewInstance(literal);
    }

    @Override
    @SpecificImpl("getLiteral")
    @WrapMinecraftMethod(
            {
                    @VersionName(name = "getRawString", end = 1400),
                    @VersionName(name = "getText", begin = 1400, end = 1403),
                    @VersionName(name = "getRawString", begin = 1403)
            })
    String getLiteralV_1900();
}

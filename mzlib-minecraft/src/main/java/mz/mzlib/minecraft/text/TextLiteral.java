package mz.mzlib.minecraft.text;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.SpecificImpl;
import mz.mzlib.util.wrapper.WrapConstructor;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(
    {
        @VersionName(name = "net.minecraft.text.LiteralText", end = 1900),
        @VersionName(name = "net.minecraft.text.Text", begin = 1900)
    })
public interface TextLiteral extends WrapperObject, Text
{
    WrapperFactory<TextLiteral> FACTORY = WrapperFactory.of(TextLiteral.class);

    static TextLiteral newInstance(String literal)
    {
        return FACTORY.getStatic().static$newInstance(literal);
    }
    TextLiteral static$newInstance(String literal);
    @SpecificImpl("static$newInstance")
    @VersionRange(end = 1900)
    @WrapConstructor
    TextLiteral static$newInstanceV_1900(String literal);
    @SpecificImpl("static$newInstance")
    @VersionRange(begin = 1900)
    default TextLiteral static$newInstanceV1900(String literal)
    {
        return TextMutableV1600.newInstanceV1900(TextContentLiteralV1900.newInstance(literal)).castTo(FACTORY);
    }

    @SuppressWarnings("deprecation")
    String getLiteral();
    @SpecificImpl("getLiteral")
    @VersionRange(end = 1900)
    @WrapMinecraftMethod(@VersionName(name = "getRawString"))
    String getLiteralV_1900();
    @SpecificImpl("getLiteral")
    @VersionRange(begin = 1900)
    default String getLiteralV1900()
    {
        return this.getContentV1900().castTo(TextContentLiteralV1900.FACTORY).getLiteral();
    }
}

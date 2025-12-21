package mz.mzlib.minecraft.text;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftInnerClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.SpecificImpl;
import mz.mzlib.util.wrapper.WrapConstructor;
import mz.mzlib.util.wrapper.WrapperFactory;

@VersionRange(begin = 1900)
@WrapMinecraftClass({
    @VersionName(name = "net.minecraft.text.LiteralTextContent", end = 2003),
    @VersionName(name = "net.minecraft.text.PlainTextContent", begin = 2003)
})
public interface TextContentLiteralV1900 extends TextContentV1900
{
    WrapperFactory<TextContentLiteralV1900> FACTORY = WrapperFactory.of(TextContentLiteralV1900.class);

    static TextContentLiteralV1900 newInstance(String literal)
    {
        return FACTORY.getStatic().static$newInstance(literal);
    }
    TextContentLiteralV1900 static$newInstance(String literal);
    @SpecificImpl("static$newInstance")
    @WrapConstructor
    @VersionRange(end = 2003)
    TextContentLiteralV1900 static$newInstanceV_2003(String literal);
    @SpecificImpl("static$newInstance")
    @WrapConstructor
    @VersionRange(begin = 2003)
    default TextContentLiteralV1900 static$newInstanceV2003(String literal)
    {
        return ImplV2003.newInstance(literal);
    }

    @WrapMinecraftMethod(@VersionName(name = "string"))
    String getLiteral();

    @VersionRange(begin = 2003)
    @WrapMinecraftInnerClass(outer = TextContentLiteralV1900.class, name = @VersionName(name = "Literal"))
    interface ImplV2003 extends TextContentLiteralV1900
    {
        WrapperFactory<ImplV2003> FACTORY = WrapperFactory.of(ImplV2003.class);

        static ImplV2003 newInstance(String literal)
        {
            return FACTORY.getStatic().static$newInstance(literal);
        }
        @WrapConstructor
        ImplV2003 static$newInstance(String literal);
    }
}

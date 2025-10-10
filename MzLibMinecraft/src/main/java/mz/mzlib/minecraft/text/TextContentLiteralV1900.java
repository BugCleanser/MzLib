package mz.mzlib.minecraft.text;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftInnerClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.*;

@WrapMinecraftClass({@VersionName(name="net.minecraft.text.LiteralTextContent", begin=1900, end=2003), @VersionName(name="net.minecraft.text.PlainTextContent", begin=2003)})
public interface TextContentLiteralV1900 extends TextContentV1900
{
    WrapperFactory<TextContentLiteralV1900> FACTORY = WrapperFactory.of(TextContentLiteralV1900.class);
    @Deprecated
    @WrapperCreator
    static TextContentLiteralV1900 create(Object wrapped)
    {
        return WrapperObject.create(TextContentLiteralV1900.class, wrapped);
    }
    
    static TextContentLiteralV1900 newInstance(String literal)
    {
        return create(null).staticNewInstance(literal);
    }
    TextContentLiteralV1900 staticNewInstance(String literal);
    @SpecificImpl("staticNewInstance")
    @WrapConstructor
    @VersionRange(end=2003)
    TextContentLiteralV1900 staticNewInstanceV_2003(String literal);
    @SpecificImpl("staticNewInstance")
    @WrapConstructor
    @VersionRange(begin=2003)
    default TextContentLiteralV1900 staticNewInstanceV2003(String literal)
    {
        return ImplV2003.newInstance(literal);
    }

    @WrapMinecraftMethod(@VersionName(name="string"))
    String getLiteral();

    @WrapMinecraftInnerClass(outer= TextContentLiteralV1900.class, name=@VersionName(name="Literal", begin=2003))
    interface ImplV2003 extends TextContentLiteralV1900
    {
        WrapperFactory<ImplV2003> FACTORY = WrapperFactory.of(ImplV2003.class);
        @Deprecated
        @WrapperCreator
        static ImplV2003 create(Object wrapped)
        {
            return WrapperObject.create(ImplV2003.class, wrapped);
        }
        
        static ImplV2003 newInstance(String literal)
        {
            return ImplV2003.create(null).staticNewInstance(literal);
        }
        @WrapConstructor
        ImplV2003 staticNewInstance(String literal);
    }
}

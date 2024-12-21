package mz.mzlib.minecraft.text;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftInnerClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.WrapConstructor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(@VersionName(name="net.minecraft.text.PlainTextContent", begin=1900))
public interface TextContentLiteralV1900 extends TextContentV1900
{
    @WrapperCreator
    static TextContentLiteralV1900 create(Object wrapped)
    {
        return WrapperObject.create(TextContentLiteralV1900.class, wrapped);
    }

    @WrapMinecraftMethod(@VersionName(name="string"))
    String getLiteral();

    @WrapMinecraftInnerClass(wrapperSupper= TextContentLiteralV1900.class, name=@VersionName(name="Literal"))
    interface Impl extends TextContentV1900
    {
        @WrapperCreator
        static Impl create(Object wrapped)
        {
            return WrapperObject.create(Impl.class, wrapped);
        }

        @WrapConstructor
        Impl staticNewInstance(String literal);
        static Impl newInstance(String literal)
        {
            return Impl.create(null).staticNewInstance(literal);
        }
    }
}

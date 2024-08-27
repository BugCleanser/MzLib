package mz.mzlib.minecraft.text;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.WrapConstructor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(@VersionName(name="net.minecraft.text.LiteralText"))
public interface TextLiteral extends WrapperObject, AbstractText
{
    @WrapperCreator
    static TextLiteral create(Object wrapped)
    {
        return WrapperObject.create(TextLiteral.class, wrapped);
    }

    @WrapConstructor
    TextLiteral staticNewInstance(String literal);
    static TextLiteral newInstance(String literal)
    {
        return create(null).staticNewInstance(literal);
    }

    @WrapMinecraftMethod(@VersionName(name="getRawString"))
    String getLiteral();
}

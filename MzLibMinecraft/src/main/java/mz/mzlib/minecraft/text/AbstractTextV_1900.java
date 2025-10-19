package mz.mzlib.minecraft.text;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.util.InvertibleFunction;
import mz.mzlib.util.proxy.ListProxy;
import mz.mzlib.util.wrapper.SpecificImpl;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

import java.util.List;

@VersionRange(end=1900)
@WrapMinecraftClass(@VersionName(name = "net.minecraft.text.BaseText"))
public interface AbstractTextV_1900 extends WrapperObject, Text
{
    WrapperFactory<AbstractTextV_1900> FACTORY = WrapperFactory.of(AbstractTextV_1900.class);
    @Deprecated
    @WrapperCreator
    static AbstractTextV_1900 create(Object wrapped)
    {
        return WrapperObject.create(AbstractTextV_1900.class, wrapped);
    }

    @WrapMinecraftFieldAccessor(@VersionName(name="siblings"))
    void setExtra0(List<Object> value);
    
    @SpecificImpl("setExtra")
    @Override
    default Text setExtraV_1900(List<Text> value)
    {
        this.setExtra0(new ListProxy<>(value, InvertibleFunction.wrapper(Text.FACTORY).inverse()));
        return this;
    }
    
    @SpecificImpl("setStyle")
    @Override
    @WrapMinecraftFieldAccessor(@VersionName(name="style"))
    void setStyleV_1900(TextStyle style);
}

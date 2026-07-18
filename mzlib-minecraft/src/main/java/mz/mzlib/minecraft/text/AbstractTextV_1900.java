package mz.mzlib.minecraft.text;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.util.FunctionInvertible;
import mz.mzlib.util.proxy.ListProxy;
import mz.mzlib.util.wrapper.Impl;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

import java.util.List;

@VersionRange(end = 1900)
@WrapMinecraftClass(@VersionName(name = "net.minecraft.text.BaseText"))
public interface AbstractTextV_1900 extends WrapperObject, Text
{
    WrapperFactory<AbstractTextV_1900> FACTORY = WrapperFactory.of(AbstractTextV_1900.class);
    @WrapMinecraftFieldAccessor(@VersionName(name = "siblings"))
    void setExtra0(List<Object> value);

    @Impl("setExtra")
    @Override
    default Text setExtraV_1900(List<Text> value)
    {
        this.setExtra0(new ListProxy<>(value, FunctionInvertible.wrapper(Text.FACTORY).inverse()));
        return this;
    }

    @Impl("setStyle")
    @Override
    @WrapMinecraftFieldAccessor(@VersionName(name = "style"))
    void setStyleV_1900(TextStyle style);
}


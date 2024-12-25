package mz.mzlib.minecraft.text;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.*;

import java.util.List;

@WrapMinecraftClass(
        {
                @VersionName(name = "net.minecraft.text.BaseText", end=1400),
                @VersionName(name = "net.minecraft.network.chat.BaseComponent", begin=1400, end=1403),
                @VersionName(name = "net.minecraft.text.BaseText", begin=1403, end=1900)
        })
public interface AbstractTextV_1900 extends WrapperObject
{
    @WrapperCreator
    static AbstractTextV_1900 create(Object wrapped)
    {
        return WrapperObject.create(AbstractTextV_1900.class, wrapped);
    }

    @WrapMinecraftFieldAccessor(@VersionName(name="siblings"))
    void setExtra0(List<Object> value);
    default void setExtra(List<Text> value)
    {
        this.setExtra0(new ListWrapped<>(value, Text::create));
    }
    
    @WrapMinecraftFieldAccessor(@VersionName(name="style"))
    void setStyle(TextStyle style);
}

package mz.mzlib.minecraft.text;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

// TODO ??? 什么勾八玩意 Mojang又乱改一堆
@WrapMinecraftClass(
        {
                @VersionName(name = "net.minecraft.text.Text", end=1400),
                @VersionName(name = "net.minecraft.network.chat.Component", begin=1400, end=1403),
                @VersionName(name = "net.minecraft.text.Text", begin=1403)
        })
public interface Text extends WrapperObject
{
    @WrapperCreator
    static Text create(Object wrapped)
    {
        return WrapperObject.create(Text.class, wrapped);
    }
}

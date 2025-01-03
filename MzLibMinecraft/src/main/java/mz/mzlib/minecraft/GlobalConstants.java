package mz.mzlib.minecraft;

import mz.mzlib.minecraft.bridge.game.GameVersionV1400_1904;
import mz.mzlib.minecraft.version.MinecraftVersionV1800;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass({@VersionName(name="net.minecraft.util.SharedConstants", end=1400), @VersionName(name="net.minecraft.SharedConstants", begin=1400)})
public interface GlobalConstants extends WrapperObject
{
    @WrapperCreator
    static GlobalConstants create(Object wrapped)
    {
        return WrapperObject.create(GlobalConstants.class, wrapped);
    }
    
    static GameVersionV1400_1904 getMinecraftVersionV1400_1800()
    {
        return create(null).staticGetMinecraftVersionV1400_1800();
    }
    @WrapMinecraftMethod(@VersionName(name="getGameVersion", begin=1400, end=1800))
    GameVersionV1400_1904 staticGetMinecraftVersionV1400_1800();
    static MinecraftVersionV1800 getMinecraftVersionV1800()
    {
        return create(null).staticGetMinecraftVersionV1800();
    }
    @WrapMinecraftMethod(@VersionName(name="getGameVersion", begin=1800))
    MinecraftVersionV1800 staticGetMinecraftVersionV1800();
}

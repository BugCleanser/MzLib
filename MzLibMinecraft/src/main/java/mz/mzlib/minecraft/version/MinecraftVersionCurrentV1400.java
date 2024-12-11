package mz.mzlib.minecraft.version;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.bridge.game.GameVersionV1400_1904;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(@VersionName(name="net.minecraft.MinecraftVersion", begin=1400))
public interface MinecraftVersionCurrentV1400 extends WrapperObject
{
    @WrapperCreator
    static MinecraftVersionCurrentV1400 create(Object wrapped)
    {
        return WrapperObject.create(MinecraftVersionCurrentV1400.class, wrapped);
    }
    
    @WrapMinecraftFieldAccessor(@VersionName(name="#0", begin = 1800))
    GameVersionV1400_1904 staticInstanceV_1800();
    static GameVersionV1400_1904 instanceV_1800()
    {
        return create(null).staticInstanceV_1800();
    }
    @WrapMinecraftFieldAccessor(@VersionName(name="#0", begin = 1800))
    MinecraftVersionV1800 staticInstanceV1800();
    static MinecraftVersionV1800 instanceV1800()
    {
        return create(null).staticInstanceV1800();
    }
}

package mz.mzlib.minecraft.bridge.game;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(@VersionName(name = "com.mojang.bridge.game.GameVersion", begin = 1400, end = 1904))
public interface GameVersionV1400_1904 extends WrapperObject
{
    @WrapMinecraftMethod(@VersionName(name = "getWorldVersion"))
    int getDataVersion();
}

package mz.mzlib.minecraft.version;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(@VersionName(name = "net.minecraft.GameVersion", begin = 1800))
public interface MinecraftVersionV1800 extends WrapperObject
{
    WrapperFactory<MinecraftVersionV1800> FACTORY = WrapperFactory.of(MinecraftVersionV1800.class);
    @Deprecated
    @WrapperCreator
    static MinecraftVersionV1800 create(Object wrapped)
    {
        return WrapperObject.create(MinecraftVersionV1800.class, wrapped);
    }

    @WrapMinecraftMethod({
        @VersionName(name = "method_37912", end = 2106), // Mojang: getSaveVersion
        @VersionName(name = "comp_4026", begin = 2106) // Mojang: dataVersion
    })
    DataVersionV1800 getDataVersion();
}

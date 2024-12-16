package mz.mzlib.minecraft.version;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(@VersionName(name="net.minecraft.GameVersion", begin=1800))
public interface MinecraftVersionV1800 extends WrapperObject
{
    @WrapperCreator
    static MinecraftVersionV1800 create(Object wrapped)
    {
        return WrapperObject.create(MinecraftVersionV1800.class, wrapped);
    }
    
    @WrapMinecraftMethod(@VersionName(name="getSaveVersion"))
    DataVersionV1800 getDataVersion();
}

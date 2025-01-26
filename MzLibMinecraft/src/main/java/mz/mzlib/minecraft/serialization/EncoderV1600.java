package mz.mzlib.minecraft.serialization;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(@VersionName(name="com.mojang.serialization.Encoder", begin=1600))
public interface EncoderV1600 extends WrapperObject
{
    @WrapperCreator
    static WrapperObject create(Object wrapped)
    {
        return WrapperObject.create(WrapperObject.class, wrapped);
    }
    
    @WrapMinecraftMethod(@VersionName(name="encodeStart"))
    DataResultV1600 encodeStart(DynamicOpsV1300 ops, Object object);
}

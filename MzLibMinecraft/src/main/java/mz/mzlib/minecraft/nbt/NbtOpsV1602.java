package mz.mzlib.minecraft.nbt;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.serialization.DynamicOpsV1600;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(@VersionName(name="net.minecraft.nbt.NbtOps", begin=1602))
public interface NbtOpsV1602 extends WrapperObject, DynamicOpsV1600
{
    @WrapperCreator
    static NbtOpsV1602 create(Object wrapped)
    {
        return WrapperObject.create(NbtOpsV1602.class, wrapped);
    }

    @WrapMinecraftFieldAccessor(@VersionName(name="INSTANCE"))
    NbtOpsV1602 staticInstance();
    static NbtOpsV1602 instance()
    {
        return create(null).staticInstance();
    }
}

package mz.mzlib.minecraft.nbt;

import mz.mzlib.minecraft.MinecraftServer;
import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.registry.DynamicOpsWithRegistriesV1903;
import mz.mzlib.minecraft.serialization.DynamicOpsV1400;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass({@VersionName(name="net.minecraft.datafixer.NbtOps", begin=1400, end=1602), @VersionName(name="net.minecraft.nbt.NbtOps", begin=1602)})
public interface NbtOpsV1400 extends WrapperObject, DynamicOpsV1400
{
    @WrapperCreator
    static NbtOpsV1400 create(Object wrapped)
    {
        return WrapperObject.create(NbtOpsV1400.class, wrapped);
    }

    @WrapMinecraftFieldAccessor(@VersionName(name="INSTANCE"))
    NbtOpsV1400 staticInstance();
    static NbtOpsV1400 instance()
    {
        return create(null).staticInstance();
    }
    
    static DynamicOpsWithRegistriesV1903 withRegistriesV1903()
    {
        return DynamicOpsWithRegistriesV1903.newInstance(instance(), MinecraftServer.instance.getRegistriesV1802());
    }
}

package mz.mzlib.minecraft.nbt;

import mz.mzlib.minecraft.MinecraftServer;
import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.incomprehensible.registry.DynamicOpsWithRegistriesV1903;
import mz.mzlib.minecraft.serialization.DynamicOpsV1300;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@VersionRange(begin=1300)
@WrapMinecraftClass({@VersionName(name="net.minecraft.class_4372", end=1400), @VersionName(name="net.minecraft.datafixer.NbtOps", begin=1400, end=1500), @VersionName(name="net.minecraft.datafixers.NbtOps", begin=1500, end=1501), @VersionName(name="net.minecraft.datafixer.NbtOps", begin=1501, end=1602), @VersionName(name="net.minecraft.nbt.NbtOps", begin=1602)})
public interface NbtOpsV1300 extends WrapperObject, DynamicOpsV1300
{
    WrapperFactory<NbtOpsV1300> FACTORY = WrapperFactory.find(NbtOpsV1300.class);
    @Deprecated
    @WrapperCreator
    static NbtOpsV1300 create(Object wrapped)
    {
        return WrapperObject.create(NbtOpsV1300.class, wrapped);
    }
    
    @WrapMinecraftFieldAccessor({@VersionName(name="field_21487", end=1400), @VersionName(name="INSTANCE", begin=1400)})
    NbtOpsV1300 staticInstance();
    
    static NbtOpsV1300 instance()
    {
        return create(null).staticInstance();
    }
    
    static DynamicOpsWithRegistriesV1903 withRegistriesV1903()
    {
        return DynamicOpsWithRegistriesV1903.newInstance(instance(), MinecraftServer.instance.getRegistriesV1802());
    }
}

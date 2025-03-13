package mz.mzlib.minecraft.serialization;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.WrapConstructor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@VersionRange(begin=1300)
@WrapMinecraftClass({@VersionName(name="com.mojang.datafixers.Dynamic", end=1600), @VersionName(name="com.mojang.serialization.Dynamic", begin = 1600)})
public interface DynamicV1300 extends WrapperObject
{
    WrapperFactory<DynamicV1300> FACTORY = WrapperFactory.find(DynamicV1300.class);
    @Deprecated
    @WrapperCreator
    static DynamicV1300 create(Object wrapped)
    {
        return WrapperObject.create(DynamicV1300.class, wrapped);
    }
    
    @WrapConstructor
    DynamicV1300 staticNewInstance(DynamicOpsV1300 ops);
    static DynamicV1300 newInstance(DynamicOpsV1300 ops)
    {
        return create(null).staticNewInstance(ops);
    }
    
    @WrapConstructor
    DynamicV1300 staticNewInstance(DynamicOpsV1300 ops, Object data);
    static DynamicV1300 newInstance(DynamicOpsV1300 ops, Object data)
    {
        return create(null).staticNewInstance(ops, data);
    }
    
    @WrapMinecraftMethod(@VersionName(name="getValue"))
    Object getValue();
}

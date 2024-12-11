package mz.mzlib.minecraft.serialization;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.WrapConstructor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(@VersionName(name="com.mojang.datafixers.Dynamic", begin=1400))
public interface DynamicV1400 extends WrapperObject
{
    @WrapperCreator
    static DynamicV1400 create(Object wrapped)
    {
        return WrapperObject.create(DynamicV1400.class, wrapped);
    }
    
    @WrapConstructor
    DynamicV1400 staticNewInstance(DynamicOpsV1400 ops);
    static DynamicV1400 newInstance(DynamicOpsV1400 ops)
    {
        return create(null).staticNewInstance(ops);
    }
    
    @WrapConstructor
    DynamicV1400 staticNewInstance(DynamicOpsV1400 ops, Object data);
    static DynamicV1400 newInstance(DynamicOpsV1400 ops, Object data)
    {
        return create(null).staticNewInstance(ops, data);
    }
    
    @WrapMinecraftMethod(@VersionName(name="getValue"))
    Object getValue();
}

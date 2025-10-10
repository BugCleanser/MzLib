package mz.mzlib.minecraft.serialization;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.RuntimeUtil;
import mz.mzlib.util.wrapper.WrapConstructor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@VersionRange(begin=1300)
@WrapMinecraftClass({@VersionName(name="com.mojang.datafixers.Dynamic", end=1600), @VersionName(name="com.mojang.serialization.Dynamic", begin = 1600)})
public interface DynamicV1300<T> extends WrapperObject
{
    WrapperFactory<DynamicV1300<?>> FACTORY = RuntimeUtil.cast(WrapperFactory.of(DynamicV1300.class));
    
    static <T> DynamicV1300<T> newInstance(DynamicOpsV1300<T> ops)
    {
        return FACTORY.getStatic().staticNewInstance(ops);
    }
    @WrapConstructor
    <T1> DynamicV1300<T1> staticNewInstance(DynamicOpsV1300<T1> ops);
    
    static <T> DynamicV1300<T> newInstance(DynamicOpsV1300<T> ops, T value)
    {
        return FACTORY.getStatic().staticNewInstance(ops, value);
    }
    @WrapConstructor
    <T1> DynamicV1300<T1> staticNewInstance(DynamicOpsV1300<T1> ops, T1 value);
    
    static <T extends WrapperObject> Wrapper<T> newInstance(DynamicOpsV1300.Wrapper<T> ops, T value)
    {
        return new Wrapper<>(newInstance(ops.getBase(), RuntimeUtil.cast(value.getWrapped())), ops.getType());
    }
    
    @WrapMinecraftMethod(@VersionName(name="getValue"))
    T getValue();
    
    class Wrapper<T extends WrapperObject>
    {
        DynamicV1300<?> base;
        WrapperFactory<T> type;
        public Wrapper(DynamicV1300<?> base, WrapperFactory<T> type)
        {
            this.base = base;
            this.type = type;
        }
        
        public DynamicV1300<?> getBase()
        {
            return this.base;
        }
        
        public WrapperFactory<T> getType()
        {
            return this.type;
        }
        
        public T getValue()
        {
            return this.type.create(this.base.getValue());
        }
    }
}

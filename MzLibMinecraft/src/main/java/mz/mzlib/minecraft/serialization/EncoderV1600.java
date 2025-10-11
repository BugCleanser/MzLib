package mz.mzlib.minecraft.serialization;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.RuntimeUtil;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(@VersionName(name="com.mojang.serialization.Encoder", begin=1600))
public interface EncoderV1600<T> extends WrapperObject
{
    WrapperFactory<WrapperObject> FACTORY = WrapperFactory.of(WrapperObject.class);
    @Deprecated
    @WrapperCreator
    static WrapperObject create(Object wrapped)
    {
        return WrapperObject.create(WrapperObject.class, wrapped);
    }
    
    @WrapMinecraftMethod(@VersionName(name="encodeStart"))
    <D> DataResultV1600<D> encodeStart(DynamicOpsV1300<D> ops, T object);
    default <D extends WrapperObject> DataResultV1600.Wrapper<D> encodeStart(DynamicOpsV1300.Wrapper<D> ops, T object)
    {
        return new DataResultV1600.Wrapper<>(this.encodeStart(ops.getBase(), object), ops.getType());
    }
    
    interface IWrapper<T extends WrapperObject>
    {
        EncoderV1600<?> getBase();
        
        default <D> DataResultV1600<D> encodeStart(DynamicOpsV1300<D> ops, T object)
        {
            return this.getBase().encodeStart(ops, RuntimeUtil.cast(object.getWrapped()));
        }
        default <D extends WrapperObject> DataResultV1600.Wrapper<D> encodeStart(DynamicOpsV1300.Wrapper<D> ops, T object)
        {
            return new DataResultV1600.Wrapper<>(this.encodeStart(ops.getBase(), object), ops.getType());
        }
    }
    
    class Wrapper<T extends WrapperObject> implements IWrapper<T>
    {
        EncoderV1600<?> base;
        public Wrapper(EncoderV1600<?> base)
        {
            this.base = base;
        }
        
        @Override
        public EncoderV1600<?> getBase()
        {
            return this.base;
        }
    }
}

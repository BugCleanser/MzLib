package mz.mzlib.minecraft.serialization;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.RuntimeUtil;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(@VersionName(name="com.mojang.serialization.Decoder", begin = 1600))
public interface DecoderV1600<T> extends WrapperObject
{
    WrapperFactory<DecoderV1600<?>> FACTORY = RuntimeUtil.cast(WrapperFactory.of(DecoderV1600.class));
    @Deprecated
    @WrapperCreator
    static DecoderV1600<?> create(Object wrapped)
    {
        return WrapperObject.create(DecoderV1600.class, wrapped);
    }
    
    @WrapMinecraftMethod(@VersionName(name="parse"))
    <D> DataResultV1600<T> parse(DynamicOpsV1300<D> ops, D data);
    default <D extends WrapperObject> DataResultV1600<T> parse(DynamicOpsV1300.Wrapper<D> ops, D data)
    {
        return this.parse(ops.getBase(), RuntimeUtil.cast(data.getWrapped()));
    }
    
    
    interface IWrapper<T extends WrapperObject>
    {
        DecoderV1600<?> getBase();
        WrapperFactory<T> getType();
        
        default <D> DataResultV1600.Wrapper<T> parse(DynamicOpsV1300<D> ops, D data)
        {
            return new DataResultV1600.Wrapper<>(this.getBase().parse(ops, data), this.getType());
        }
        default <U extends WrapperObject> DataResultV1600.Wrapper<T> parse(DynamicOpsV1300.Wrapper<U> ops, U data)
        {
            return this.parse(ops.getBase(), RuntimeUtil.cast(data.getWrapped()));
        }
    }
    
    class Wrapper<T extends WrapperObject> implements IWrapper<T>
    {
        DecoderV1600<?> base;
        WrapperFactory<T> type;
        public Wrapper(DecoderV1600<?> base, WrapperFactory<T> type)
        {
            this.base = base;
            this.type = type;
        }
        
        @Override
        public DecoderV1600<?> getBase()
        {
            return this.base;
        }
        
        @Override
        public WrapperFactory<T> getType()
        {
            return this.type;
        }
    }
}
